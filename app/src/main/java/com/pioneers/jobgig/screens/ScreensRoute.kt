package com.pioneers.jobgig.screens

sealed class ScreenRoute(var route: String){
    object  GetStarted: ScreenRoute("getStarted")
    object  Login: ScreenRoute("login")
    object  ForgetPassword: ScreenRoute("forget_password")
    object  Signup: ScreenRoute("signup")
    object  Auth:ScreenRoute("auth")
    object  HomeEntry:ScreenRoute("home")
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
    object InstructorDetailLScreenRoute:ScreenRoute("instructor_detail/{uri}/{about}"){
        fun addUri(uri:String,about:String): String {
            return "instructor_detail/$uri/$about"
        }
    }


    object ServiceSearch:ScreenRoute("service_search")
    object ServiceVocOnline:ScreenRoute("service_voc_online")
    object ServiceVocInfo:ScreenRoute("service_voc_info")
    object ServiceSession:ScreenRoute("service_session")
    object ServiceChat:ScreenRoute("service_chat")
    object ServiceRate:ScreenRoute("service_rate")



    object ProfileEdit:ScreenRoute("profile_edit")//done
    object Donate:ScreenRoute("donate")//done
    object DonateSeek:ScreenRoute("donate_seek")//dones
    object DonateGive:ScreenRoute("donate_give/{type}"){
        fun route(type:String): String {
            return "donate_give/$type"
        }
    }
    object DonateWhy:ScreenRoute("donate_why")//done

    object VocDashBoard:ScreenRoute("voc_dashboard")//dones
    object VocRequest:ScreenRoute("voc_request")//dones
    object CreateTutorial:ScreenRoute("create_tut")//dones
    object UploadTutorial:ScreenRoute("upload_tut")//dones
    object GigAlert:ScreenRoute("gig_alert")


}