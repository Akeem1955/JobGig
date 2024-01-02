package com.pioneers.jobgig.dataobj.retrofit


data class LatLng(val latitude: Double, val longitude: Double)

data class Location(val latLng: LatLng)

data class Destination(val location: Location)

data class Origin(val location: Location)

data class RouteModifiers(
    val avoidTolls: Boolean,
    val avoidHighways: Boolean,
    val avoidFerries: Boolean
)

data class Request(
    val origin: Origin,
    val destination: Destination,
    val travelMode: String,
    val routingPreference: String,
    val departureTime: String,
    val computeAlternativeRoutes: Boolean,
    val routeModifiers: RouteModifiers,
    val languageCode: String,
    val units: String
)
data class Polyline(var encodedPolyline: String)
data class Routes(var distanceMeters:String, var duration:String , var polyline:Polyline)
data class RouteRes(var routes:ArrayList<Routes>)

