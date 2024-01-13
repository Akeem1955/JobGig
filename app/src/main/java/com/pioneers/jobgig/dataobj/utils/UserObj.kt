package com.pioneers.jobgig.dataobj.utils

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class Comment(val name:String, val profilePic:String, val comment:String, val rating:Double)

data class Course(val temp:Int)
data class User(val profilePic:String?,
                val fullname:String?,
                val rating: Double?,
                val currentLocation:LatLng?,
                val online:Boolean?,
                val comments:List<Comment>?,
                val imageOfPrevWork:List<String>?,
                val enrolledCourses:List<Course>?,
                val verified:Boolean?,
                val locationSum:Double?
    )