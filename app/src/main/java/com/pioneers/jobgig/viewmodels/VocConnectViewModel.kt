package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
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
import com.pioneers.jobgig.screens.ScreenRoute
import com.pioneers.jobgig.screens.VocationalCategory
import com.pioneers.jobgig.viewmodels.OnBoardViewModel.Companion.currentUser
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import kotlin.time.Duration.Companion.seconds

class VocConnectViewModel:ViewModel() {
    val uuid = UUID.randomUUID().toString()
    val updates = Firebase.firestore

    //needed variables


    var loadingState by mutableStateOf(true)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")
    var polyline by mutableStateOf(emptyList<LatLng>())
    var yourType by mutableStateOf(ClientType.Client)
    var clientType = derivedStateOf {
        when(yourType){
            ClientType.Client -> ClientType.Worker
            ClientType.Worker -> ClientType.Client
        }
    }
    var thisUser: User = currentUser.value
    var transactSession by mutableStateOf(QuickSession(fullname = thisUser.fullname, profilePic = thisUser.profilePic))
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://routes.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val db = Firebase.firestore.collection("Users")
    private var _availableworkerQuery = db.limit(20)
    private var availableworkerQuery = _availableworkerQuery
    var thatUser: User = User()
    private var _thoseUser = MutableStateFlow(mutableStateListOf<DocumentSnapshot>())
    var availableWorker = mutableStateListOf<AvailableWorker>()

    private var thoseUser = mutableStateListOf<User>()
//    private val nots =derivedStateOf{
//        thoseUser.forEachIndexed { index, user ->
//            println("we are here")
//            if (user.currentLocation.latitude != 0.0 && user.currentLocation.longitude != 0.0 && index >= availableWorker.size){
//                viewModelScope.launch {
//                    val route =
//                        getRoute(thisUser.currentLocation, user.currentLocation) ?: return@launch
//                    availableWorker.add(AvailableWorker(user.profilePic,user.fullname,user.rating,route.distanceMeters?:"0 meter",route.duration?:"0 sec", pos = index))
//                }
//            }
//        }
//    }
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
        viewModelScope.launch(Dispatchers.IO) {
            loadingState =  true
            try {
                availableworkerQuery = _availableworkerQuery.whereEqualTo("vocationalType",query).whereEqualTo("online",true)
                    .whereGreaterThanOrEqualTo("locationSum", lower)
                    .whereLessThanOrEqualTo("locationSum", upper)
                _thoseUser.value = availableworkerQuery.get().await().documents.toMutableStateList()
                println("level 1 searching for worker...")
                println(_thoseUser.value.toList())
                thoseUser = _thoseUser.value.mapNotNull { doc ->
                    doc.toObject<User>()
                }.toMutableStateList()
                thoseUser.forEachIndexed { index, user ->
                    println("we are here")
                    if (user.currentLocation.latitude != 0.0 && user.currentLocation.longitude != 0.0 && index >= availableWorker.size){
                        val route =
                            getRoute(thisUser.currentLocation, user.currentLocation)!!
                        availableWorker.add(AvailableWorker(user.profilePic,user.fullname,user.rating,route.distanceMeters?:"0 meter",route.duration?:"0 sec", pos = index))

                    }
                }
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
        currentUser.value.currentLocation  = LatLngs(latitude,longitude)
        updateUser()

    }

    fun getPolygonLine() {
        PolyUtil.decode("")
        if (thatUser.currentLocation.latitude == 0.0 && thatUser.currentLocation.longitude == 0.0) return
        loadingState = true
        viewModelScope.launch(Dispatchers.IO) {
          try {
              polyline = PolyUtil.decode(  getRoute(thisUser.currentLocation, thatUser.currentLocation)?.polyline?.encodedPolyline ?: "" )
              loadingState = false
          }catch (e:Exception){
              e.printStackTrace()
              println(e.message)
          }
        }
    }
    private fun updateUser(){
        viewModelScope.launch {
            try {
                Firebase.firestore.collection("Users").document(thisUser.uid).set(currentUser.value).await()
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
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
            departureTime = formatTimestampToDate(Timestamp.now()),
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
            println("About to get the route")
            val response = call.execute()
            route = response.body()?.routes?.get(0)
            println("We got the route ")
            println(" route is ${route?.distanceMeters} ${route?.polyline?.encodedPolyline}, ${route?.duration} ${route == null} ")
            println("sucess:${response.isSuccessful} , code:${response.code()}, msg:${response.message()},error:${response.errorBody()?.string()} ")

        } catch (e: Exception) {
            e.printStackTrace()
            println(e.message)
            println("error happen what happpen now................................................")
        }
        return route

    }

    var alertSucessfull by mutableStateOf(AlertWorkerState.Init)
    private var sessionSnapshotListener: ListenerRegistration? = null
    private var sessionPath:String =""


    fun sendJobAlert(){
        val uid = Firebase.auth.currentUser?.uid ?: currentUser.value.uid
        if(uid == "" || thatUser.uid == "")return
        val sessions = db.document(uid).collection("Session").document()
        viewModelScope.launch {
            try {
                initiateSessionListener(sessions.path)
                sessions.set(QuickSession(fullname = thisUser.fullname, profilePic = thisUser.profilePic, initID = thisUser.uid, initComments = thisUser.comments, initRating = thisUser.rating)).await()
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
        thatUser = thoseUser[pos]
    }
    fun initiateSessionListener(path:String){
        sessionPath = path
        println("Initiating session with my path wawooo....... $sessionPath")
        sessionSnapshotListener = Firebase.firestore.document(path).addSnapshotListener { value, error ->
            if (error != null) {
                error.printStackTrace()
                println(error.message)
                return@addSnapshotListener
            }
            if (value != null && value.exists()){
                try {
                    transactSession = value.toObject<QuickSession>() ?: transactSession
                }catch (e:Exception){
                    e.printStackTrace()
                    println(e.message)
                }
            }
        }
    }
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
        println(throwable.message)
    }
    fun updateSession(){
        loadingState=true
        try {
            println("Before level 2")
            updates.document(sessionPath).set(transactSession)
            println("sestionpath-> ${uuid.isBlank()} ${uuid.length} $uuid")
            println("level 2")
            loadingState=false
        }catch (e:Exception){
            loadingState=false
            errorState=true
            errorMsg = "Ouch!!! unexpected error check connection and retry"
            e.printStackTrace()
            println(e.message)
        }
    }
    private fun formatTimestampToDate(timestamp: Timestamp): String {
        val time = timestamp.toDate().time + 20000
        val date = Date(time)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(date)
    }

    fun payWorker(){
        transactSession.payed = true
        updateSession()
    }
    override fun onCleared() {
        sessionSnapshotListener?.remove()
        super.onCleared()
    }





    fun reviewUser(navController: NavController){
        if (thatUser.uid.isBlank()){
            navController.navigate(ScreenRoute.HomeEntry.route){
                popUpTo(ScreenRoute.HomeEntry.route){
                    inclusive=true
                }
            }
            return
        }
        loadingState = true
        viewModelScope.launch {
            try {
                Firebase.firestore.collection("Users").document(thatUser.uid).set(thatUser).await()
                loadingState = false
                navController.navigate(ScreenRoute.HomeEntry.route){
                    popUpTo(ScreenRoute.HomeEntry.route){
                        inclusive=true
                    }
                }
            }catch (e:Exception){
                loadingState = false
                navController.navigate(ScreenRoute.HomeEntry.route){
                    popUpTo(ScreenRoute.HomeEntry.route){
                        inclusive=true
                    }
                }
                e.printStackTrace()
                println(e.message)
            }
        }
    }
}

