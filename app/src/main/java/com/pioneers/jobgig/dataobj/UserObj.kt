package com.pioneers.jobgig.dataobj

import android.net.Uri
import com.google.android.gms.maps.model.LatLng

data class Comment(val name:String, val profilePic:Uri, val comment:String, val rating:Double)

data class Course(val temp:Int)
data class User(val profilePic:Uri,
                val rating: Double,
                val currentLocation:LatLng,
                val online:Boolean,
                val comments:List<Comment>,
                val imageOfPrevWork:List<Uri>,
                val enrolledCourses:List<Course>,
                val verified:Boolean,
                val locationSum:Double
    )