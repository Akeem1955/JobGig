package com.pioneers.jobgig.dataobj.retrofit

import com.pioneers.jobgig.BuildConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RouteReq {
    @Headers(
        "Content-Type: application/json",
        "X-Goog-Api-Key: ${BuildConfig.maps_api_key}",
        "X-Goog-FieldMask: routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
    @POST("directions/v2:computeRoutes")
    fun getCompute(@Body data:Request): Call<RouteRes>
}