package com.pioneers.jobgig.sealed

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

    data object DonateMoney : HomeCardViews(
        "Donate",
        "Providing help with acquiring vocational tools.",
        R.drawable.flat_color_icons_donate,
        ""
    )

    data object DonateAsk : HomeCardViews(
        "Request For Assistance",
        "your request will be placed in the queue.",
        R.drawable.carbon_need,
        ""
    )

    data object Donate :
        HomeCardViews("Donate", "Help a vocational worker.", R.drawable.flat_color_icons_donate, "")


    data object ProvideWorkspace : HomeCardViews(
        "Provide Workspace",
        "Be a Workspace Hero by sponsoring vocational workspaces for aspiring professionals",
        R.drawable.arcticons_nrf_toolbox,
        ""
    )

    data object GetAGig : HomeCardViews(
        "Searching For Jobs ?",
        "Submit your Info and Get A gig",
        R.drawable.job_search,
        ""
    )

    data object Courses : HomeCardViews(
        "Online Tutorial",
        "Get to learn any skill of your choice",
        R.drawable.online_course,
        ""
    )

    data object VocaSage :
        HomeCardViews("Voca Sage", "Interact with VocaSage AI", R.drawable.online_course, "")

    data object Service : HomeCardViews(
        "Need A Service ?",
        "Hire someone to do the job for you",
        R.drawable.noto_hammer_and_wrench,
        ""
    )

    data object Dashboard :
        HomeCardViews("Manage Incoming Gigs", "", R.drawable.noto_hammer_and_wrench, "")

    data object CreateCourse : HomeCardViews(
        "Create A Course",
        "Share knowledge and tutor others",
        R.drawable.noto_hammer_and_wrench,
        ""
    )

}