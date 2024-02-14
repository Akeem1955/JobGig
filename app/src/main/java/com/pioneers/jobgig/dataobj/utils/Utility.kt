package com.pioneers.jobgig.dataobj.utils

import android.net.Uri
import com.pioneers.jobgig.screens.DonateType


data class InstructorDesignData(var uri: String ="", var name:String="", var description:String="")
data class Category(var color:String="", var icon:String="", var userEnrolled:Int=0)
data class  CategoryItems(var categories:List<Category> = emptyList())
data class DonationQueue(var donationQueue:List<DonationQueue> = emptyList())
data class DonationRequest(var reason:String="",
                           var target:Double = 0.0,
                           var raised:Double = 0.0,
                           var profile:String="",
                           var uid:String="",
                           var spaceaddress:String="",
                           var spacemail:String="",
                           var spacephone:String="",
                           var name:String="",
                           var type:String=DonateType.CraftSpace.name)