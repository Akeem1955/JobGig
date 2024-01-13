package com.pioneers.jobgig.dataobj.utils


data class CourseData(val title: String,
                      val imageUri:String,
                      val requirement:List<String> = emptyList(),
                      val forWho:List<String> = emptyList(),
                      val duration: String,
                      val learners:Int,
                      val instructorInfo:List<InstructorDesignData> = emptyList(),
                      val numOfRate:Int,
                      val rating:Double,
                      val comments:List<Comment> = emptyList(),
                      val description:String,
                      val intro:String,
                      val content:List<CourseContent> = emptyList(),
                      val whatLearn:List<String> = emptyList()
)
data class CourseContent(val title:String, val uri: String, val duration:String)