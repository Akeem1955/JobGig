package com.pioneers.jobgig.dataobj.utils

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
                var tutComments:List<Comment> = emptyList(),
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
data class Trends(var trends: List<Trend> = emptyList())

data class Trend(
    var skill: String = "",
    var hired_daily: Double = 0.0,
    val hired_monthly: Double = 0.0,
    var all_time: Double = 0.0,
    var average_time: Double = 0.0,
    var average_pay: Double = 0.0
)


val trendData = listOf(
    Trend("Carpentry", 100.0, 10000.0, 47000.0, 67.7, 47780.0),
    Trend("Aluminium", 100.0, 8000.0, 55000.0, 62.0, 56780.0),
    Trend("Plumbing", 110.0, 7500.0, 52000.0, 65.0, 53780.0),
    Trend("Landscaping", 90.0, 7000.0, 50000.0, 60.0, 50780.0),
    Trend("Painting", 105.0, 7500.0, 57000.0, 63.0, 58780.0),
    Trend("Ac", 95.0, 8500.0, 58000.0, 58.0, 59780.0),
    Trend("Catering", 115.0, 10000.0, 60000.0, 70.0, 61780.0),
    Trend("Automotive", 125.0, 9000.0, 62000.0, 75.0, 63780.0),
    Trend("Bricklayer", 85.0, 7500.0, 50000.0, 55.0, 50780.0),
    Trend("Knitting", 95.0, 6000.0, 48000.0, 60.0, 48780.0),
    Trend("ShoeMaker", 105.0, 6500.0, 46000.0, 68.0, 46780.0),
    Trend("Welding", 110.0, 8000.0, 53000.0, 72.0, 54780.0),
    Trend("Mechanic", 120.0, 9500.0, 55000.0, 78.0, 56780.0),
    Trend("Electrical", 100.0, 8500.0, 59000.0, 64.0, 60780.0),
    Trend("Fashion", 130.0, 11000.0, 64000.0, 80.0, 65780.0),
    Trend("Salon", 110.0, 7500.0, 57000.0, 72.0, 58780.0),
    Trend("Photography", 115.0, 8000.0, 59000.0, 74.0, 60780.0)
)

data class Feedback(
    var features: List<String> = emptyList(),
    var appBuggy: Int = 0,
    var appGood: Int = 0,
    var neutral: Int = 0
)