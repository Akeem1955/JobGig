package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.pioneers.jobgig.dataobj.utils.DonationRequest
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.DonateType
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DonateViewModel :ViewModel(){
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")
    private val db = Firebase.firestore.collection("Donations")
    private var donationQuery = db.orderBy("raised", Query.Direction.DESCENDING).limit(20)
    var targets:HashMap<String,Double> = hashMapOf()

    private var donationSnapshot = mutableStateListOf<DocumentSnapshot>()
    var donationsWorkspace = derivedStateOf {
        donationSnapshot.mapNotNull {
            it.toObject<DonationRequest>()
        }.filter {
            it.type == DonateType.CraftSpace.name
        }
    }
    var donations = derivedStateOf {
        donationSnapshot.mapNotNull {
            it.toObject<DonationRequest>()
        }.filter {
            it.type != DonateType.CraftSpace.name
        }
    }



    init {
        viewModelScope.launch {
            try {
                loadingState = true
                donationSnapshot =donationQuery.get().await().documents.toMutableStateList()
                loadingState = false
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
        }
    }

    fun reload(){
        viewModelScope.launch {
            try {
                loadingState = true
                donationSnapshot =donationQuery.get().await().documents.toMutableStateList()
                loadingState = false
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
            }
        }
    }

    fun addDonationReq(req:DonationRequest){
       viewModelScope.launch {
           try {
               loadingState=true
               db.add(req).await()
               loadingState=false
           }catch (e:Exception){
               e.printStackTrace()
               println(e.message)
               loadingState = false
               errorState = true
               errorMsg="ouch!!! unexpected error happen when sending your request check your internet connection and retry"
           }
       }
    }
}