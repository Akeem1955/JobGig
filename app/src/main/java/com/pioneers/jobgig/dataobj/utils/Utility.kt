package com.pioneers.jobgig.dataobj.utils

import android.net.Uri

data class InstructorDesignData(val uri: String, val name:String, val description:String)

data class Category(val color:String, val icon:String, val userEnrolled:Int)

data class  CategoryItems(val categories:List<Category>)