package com.pioneers.jobgig.sealed

import com.pioneers.jobgig.dataobj.utils.InstructorDesignData

sealed interface CourseContentDesign{
    data class ListDesign(val items:List<String>):CourseContentDesign
    data class TextDesign(val item:String):CourseContentDesign
    data class InstructorDesign(val items: List<InstructorDesignData>):CourseContentDesign
    object Items:CourseContentDesign
    object Single:CourseContentDesign
    object Instructor:CourseContentDesign
}