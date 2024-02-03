package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.maps.android.PolyUtil
import com.pioneers.jobgig.QuickSession
import com.pioneers.jobgig.dataobj.retrofit.Destination
import com.pioneers.jobgig.dataobj.retrofit.Location
import com.pioneers.jobgig.dataobj.retrofit.Origin
import com.pioneers.jobgig.dataobj.retrofit.Request
import com.pioneers.jobgig.dataobj.retrofit.RouteModifiers
import com.pioneers.jobgig.dataobj.retrofit.RouteReq
import com.pioneers.jobgig.dataobj.retrofit.Routes
import com.pioneers.jobgig.dataobj.utils.AvailableWorker
import com.pioneers.jobgig.dataobj.utils.LatLngs
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.AlertWorkerState
import com.pioneers.jobgig.screens.ClientType
import com.pioneers.jobgig.screens.VocationalCategory
import com.pioneers.jobgig.viewmodels.OnBoardViewModel.Companion.currentUser
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class VocConnectViewModel:ViewModel() {
    val uuid = UUID.randomUUID().toString()

    //needed variables


    var loadingState by mutableStateOf(true)
    var polyline by mutableStateOf(emptyList<LatLng>())
    var clientType by mutableStateOf(ClientType.Worker)
    var yourType = derivedStateOf {
        when(clientType){
            ClientType.Client -> ClientType.Worker
            ClientType.Worker -> ClientType.Client
        }
    }
    var thisUser: User = currentUser
    var transactSession by mutableStateOf(QuickSession(fullname = thisUser.fullname, profilePic = thisUser.profilePic))
        private set
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://routes.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val db = Firebase.firestore.collection("Users")
    private var _availableworkerQuery = db.limit(20)
    private var availableworkerQuery = _availableworkerQuery
    var thatUser: User = User()
    private var _thoseUser = MutableStateFlow(mutableStateListOf<DocumentSnapshot>())
    var availableWorker = mutableStateListOf<AvailableWorker>()

    private var thoseUser = derivedStateOf {
        _thoseUser.value.mapNotNull { doc ->
            doc.toObject<User>()
        }
    }
    private val nots =derivedStateOf{
        thoseUser.value.forEachIndexed { index, user ->
            if (user.currentLocation.latitude != 0.0 && user.currentLocation.longitude != 0.0 && index >= availableWorker.size){
                viewModelScope.launch {
                    val route =
                        getRoute(thisUser.currentLocation, user.currentLocation) ?: return@launch
                    availableWorker.add(AvailableWorker(user.profilePic,user.fullname,user.rating,route.distanceMeters,route.duration, pos = index))
                }
            }
        }
    }
    var thoseUserLatLng = derivedStateOf {
        _thoseUser.value.mapNotNull { doc ->
            doc.toObject<User>()?.currentLocation?.let { LatLng(it.latitude,it.longitude) }
        }
    }
        private set
    private val _vocCategory = mutableStateOf(enumValues<VocationalCategory>())
    val vocCategory by derivedStateOf {
        _vocCategory.value.map {
            it.name.replace("0", " ")
        }
    }
    var query by mutableStateOf("")
        private set
    var filteredVoc by mutableStateOf(emptyList<String>())
        private set
    private var locationState by mutableStateOf(LatLng(1.35, 103.87))
    val locState
        get() = locationState





    //logic for all service screen


    fun onSearch() {
        val upper = thisUser.locationSum.plus(0.15)
        val lower = thisUser.locationSum.minus(0.15)
        viewModelScope.launch {
            loadingState =  true
            try {
                availableworkerQuery = _availableworkerQuery.whereEqualTo("vocationalType",query)
                    .whereGreaterThanOrEqualTo("locationSum", lower)
                    .whereLessThanOrEqualTo("locationSum", upper)
                _thoseUser.value = availableworkerQuery.get().await().documents.toMutableStateList()
                loadingState = false
            } catch (e: Exception) {
                e.printStackTrace()
                println(e.message)
                loadingState = false
            }
        }
    }

    private fun filteredCategory() {
        filteredVoc = vocCategory.filter {
            it.contains(query, true)
        }
    }

    fun updateQuery(que: String) {
        if (que == "") {
            query = que; filteredVoc = emptyList();return
        }
        query = que
        filteredCategory()
    }

    fun updateLatLng(latitude: Double, longitude: Double) {
        locationState = LatLng(latitude, longitude)
    }

    fun getPolygonLine() {
        if (thatUser.currentLocation.latitude == 0.0 && thatUser.currentLocation.longitude == 0.0) return
        loadingState = true
        viewModelScope.launch {
            polyline = PolyUtil.decode(getRoute(thisUser.currentLocation, thatUser.currentLocation)?.polyline?.encodedPolyline)
            loadingState = false
        }
    }

    private fun getRoute(currentUser:LatLngs, otherUser: LatLngs): Routes? {
        var route: Routes? = null
        val routeReq: RouteReq = retrofit.create(RouteReq::class.java)
        val request = Request(
            origin = Origin(
                Location(
                    com.pioneers.jobgig.dataobj.retrofit.LatLng(
                        otherUser.latitude,
                        otherUser.longitude
                    )
                )
            ),
            destination = Destination(
                Location(
                    com.pioneers.jobgig.dataobj.retrofit.LatLng(
                        currentUser.latitude,
                        currentUser.longitude
                    )
                )
            ),
            travelMode = "DRIVE",
            routingPreference = "TRAFFIC_AWARE",
            departureTime = "2023-12-21T15:01:23.045123456Z",
            computeAlternativeRoutes = false,
            routeModifiers = RouteModifiers(
                avoidTolls = false,
                avoidHighways = false,
                true
            ),
            languageCode = "en-US",
            units = "IMPERIAL"
        )
        val call = routeReq.getCompute(request)
        try {
            val response = call.execute()
            route = response.body()?.routes?.get(0)

        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
        }
        return route

    }

    var alertSucessfull by mutableStateOf(AlertWorkerState.Init)
    private var sessionSnapshotListener: ListenerRegistration? = null
    private var sessionPath:String = ""


    fun sendJobAlert(){
        val uid = Firebase.auth.currentUser?.uid ?:""
        if(uid == "" || thatUser.uid == "")return
        val sessions = db.document(uid).collection("Session").document()
        viewModelScope.launch {
            try {
                initiateSessionListener(sessions.path)
                sessions.set(QuickSession(fullname = thisUser.fullname, profilePic = thisUser.profilePic)).await()
                sendNotification(sessions.path)
            }catch (e:Exception){
               e.printStackTrace()
               println(e.message)
                alertSucessfull = AlertWorkerState.Failed
            }
        }
    }
    private fun sendNotification(sessionId:String){
        viewModelScope.launch {
            alertSucessfull = try {
                db.document(thatUser.uid).update("alert",sessionId).await()
                AlertWorkerState.Sucess
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
                AlertWorkerState.Failed
            }
        }
    }
    fun selectUser(pos:Int){
        thatUser = thoseUser.value[pos]
    }
    fun initiateSessionListener(path:String){
        sessionPath = path
        sessionSnapshotListener = db.document(path).addSnapshotListener { value, error ->
            if (error != null) {
                error.printStackTrace()
                println(error.message)
                return@addSnapshotListener
            }
            transactSession = value?.toObject<QuickSession>() ?: transactSession
        }
    }
    fun updateSession(){
        viewModelScope.launch {
            try {
                db.document(sessionPath).set(transactSession).await()
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
        }
    }

    fun payWorker(){}
    override fun onCleared() {
        sessionSnapshotListener?.remove()
        super.onCleared()
    }

}

