package com.pioneers.jobgig.sealed

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Handyman
import androidx.core.graphics.BitmapCompat
import coil.compose.ImagePainter
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
    object DonateMoney:HomeCardViews("Donate", "Providing help with acquiring vocational tools.",R.drawable.flat_color_icons_donate,"")
    object DonateAsk:HomeCardViews("Request For Assistance", "your request will be placed in the queue.",R.drawable.carbon_need,"")
    object Donate:HomeCardViews("Donate", "Help a vocational worker.",R.drawable.flat_color_icons_donate,"")


    object  ProvideWorkspace:HomeCardViews("Provide Workspace","Be a Workspace Hero by sponsoring vocational workspaces for aspiring professionals",R.drawable.arcticons_nrf_toolbox,"")
    object  GetAGig:HomeCardViews("Searching For Jobs ?", "Submit your Info and Get A gig", R.drawable.job_search,"")
    object  Courses:HomeCardViews("Online Tutorial", "Get to learn any skill of your choice", R.drawable.online_course,"")
    object  Service:HomeCardViews("Need A Service ?", "Hire someone to do the job for you",R.drawable.noto_hammer_and_wrench,"")
    object  Dashboard:HomeCardViews("Manage Incoming Gigs", "", R.drawable.noto_hammer_and_wrench,"")
    object  CreateCourse:HomeCardViews("Create A Course","Share knowledge and tutor others", R.drawable.noto_hammer_and_wrench,"")

}