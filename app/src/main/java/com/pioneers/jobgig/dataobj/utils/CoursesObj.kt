package com.pioneers.jobgig.dataobj.utils


data class CourseData(var title: String="",
                      var imageUri:String="",
                      var requirement:List<String> = emptyList(),
                      var forWho:List<String> = emptyList(),
                      var duration: String="",
                      var learners:Int=0,
                      var instructorInfo:List<InstructorDesignData> = emptyList(),
                      var numOfRate:Int=0,
                      var rating:Double=0.0,
                      var keyword:List<String> = emptyList() ,
                      var comments:List<Comment> = emptyList(),
                      var description:String="",
                      var intro:String="",
                      var content:List<CourseContent> = emptyList(),
                      var whatLearn:List<String> = emptyList()
)
data class CourseContent(var title:String="", var uri: String="", var duration:String ="")