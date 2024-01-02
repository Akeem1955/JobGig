package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.pioneers.jobgig.BuildConfig
import com.pioneers.jobgig.dataobj.retrofit.Destination
import com.pioneers.jobgig.dataobj.retrofit.Location
import com.pioneers.jobgig.dataobj.retrofit.Origin
import com.pioneers.jobgig.dataobj.retrofit.Request
import com.pioneers.jobgig.dataobj.retrofit.RouteModifiers
import com.pioneers.jobgig.dataobj.retrofit.RouteReq
import com.pioneers.jobgig.dataobj.retrofit.Routes
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewModel:ViewModel() {
    private var route: Routes? = null
    private var locationState by mutableStateOf(LatLng(1.35, 103.87))
     val locState
         get() = locationState

    fun updateLatLng(latitude:Double, longitude:Double){
       locationState = LatLng(latitude, longitude)
        BuildConfig.maps_api_key
    }
    fun GetPolygonLine(currentUser:LatLng,otherUser:LatLng):List<LatLng>?{
        viewModelScope.launch {
            if (route == null) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://routes.googleapis.com/")
                    .addConverterFactory(GsonConverterFactory.create()).build()
                val routeReq: RouteReq = retrofit.create(RouteReq::class.java)
                val request = Request(
                    origin = Origin(
                        Location(
                            com.pioneers.jobgig.dataobj.retrofit.LatLng(
                                6.6254,
                                3.3119
                            )
                        )
                    ),
                    destination = Destination(
                        Location(
                            com.pioneers.jobgig.dataobj.retrofit.LatLng(
                                6.6254,
                                3.3119
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
                        false
                    ),
                    languageCode = "en-US",
                    units = "IMPERIAL"
                )
                val call = routeReq.getCompute(request)
                try {
                    val response = call.execute()
                    route = response.body()?.routes?.get(0)
                } catch (e: Exception) {
                    println(e.message)
                }

            }
        }
        return PolyUtil.decode(route?.polyline?.encodedPolyline)
    }

}