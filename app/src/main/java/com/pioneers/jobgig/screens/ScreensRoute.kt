package com.pioneers.jobgig.screens

sealed class ScreenRoute(var route: String){
    object  GetStarted: ScreenRoute("getStarted")
    object  Login: ScreenRoute("login")
    object  ForgetPassword: ScreenRoute("forget_password")
    object  Signup: ScreenRoute("signup")
    object  Auth:ScreenRoute("auth")
    object  Home:ScreenRoute("home")
    object  Main:ScreenRoute("main")
    object  HomeScreenCourse:ScreenRoute("home_online_course")
    object  SearchCourse:ScreenRoute("searchCourses")
    object  SearchCourseResult:ScreenRoute("searchCoursesResult/{query}"){
        fun query(query:String): String {
            return "searchCoursesResult/$query"
        }
    }
    object  AllCategory:ScreenRoute("all_category")
    object  AllPopular:ScreenRoute("all_popular")
    object  EnrollPreview:ScreenRoute("enrollPreview/{index}/{type}"){
        fun enrollIndex(position:Int,type:String): String {
            return "enrollPreview/$position/$type"
        }
    }
    object CourseScreen:ScreenRoute("course_screen/{position}/{type}"){
        fun courseArgs(type: String, position: Int): String {
            return "course_screen/$position/$type"
        }
    }
    object EnrolledConfirmed:ScreenRoute("enrolled_sucess/{position}/{type}"){
        fun enrollIndex(position:Int,type:String): String {
            return "enrolled_sucess/$position/$type"
        }
    }
    object InstructorDetailLScreenRoute:ScreenRoute("instructor_detail/{uri}"){
        fun addUri(uri:String): String {
            return "instructor_detail/$uri"
        }
    }

}