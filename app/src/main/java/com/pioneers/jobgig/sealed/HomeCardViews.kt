package com.pioneers.jobgig.sealed

import androidx.annotation.DrawableRes
import com.pioneers.jobgig.R

sealed class HomeCardViews(title: String, label: String, icon:Int, actionRoute:String) {
    lateinit var title:String
        private set
    lateinit var label: String
        private set
    var icon: Int? = null
        private set
    lateinit var actionRoute:String
        private set
    init {
        this.title = title
        this.label = label
        this.icon = icon
        this.actionRoute =actionRoute
    }

    object  GetAGig:HomeCardViews("Searching For Jobs ?", "Submit your Info and Get A gig", R.drawable.job_search,"")
    object  Courses:HomeCardViews("Online Courses", "Get to learn any skill of your choice", R.drawable.online_course,"")
    object  Service:HomeCardViews("Need A Service ?", "Hire someone to do the job for you", R.drawable.noto_hammer_and_wrench,"")
    object  Dashboard:HomeCardViews("Manage Incoming Gigs", "", R.drawable.noto_hammer_and_wrench,"")
    object  CreateCourse:HomeCardViews("Create A Course","Share knowledge and tutor others", R.drawable.noto_hammer_and_wrench,"")

}