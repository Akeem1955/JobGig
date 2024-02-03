package com.pioneers.jobgig.dataobj.utils

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.google.api.ResourceReference

data class Comment(var name:String="", var profilePic:String?="", var comment:String ="", var rating:Double = 0.0)

data class Course(var temp:Int? = null)
data class User(var profilePic:String = "",
                var tutCreated:Boolean = false,
                var tutList: String="",
                var phone:String="",
                var email:String="",
                var fullname:String = "",
                var rating: Double = 0.0,
                var currentLocation:LatLngs = LatLngs(0.0,0.0),
                var online:Boolean = false,
                var comments:List<Comment> = emptyList(),
                var imageOfPrevWork:List<String> = emptyList(),
                var enrolledCourses:List<Course> = emptyList(),
                var verified:Boolean = false,
                var locationSum:Double = 0.0,
                var currentSession:String = "",
                var vocationalType:String ="",
                var address:String ="",
                var description:String="",
                var introVideo:String = "",
                var uid:String ="",
                var alert:String=""
    )


data class AvailableWorker(var profilePic: String, var name:String, var rating: Double,var distance:String, var duration:String, var pos:Int )