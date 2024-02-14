package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.pioneers.jobgig.dataobj.utils.CourseData
import com.pioneers.jobgig.dataobj.utils.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.webrtc.NetworkMonitor.init

class DashboardViewmodel:ViewModel() {
    var data = mutableStateOf(CourseData())
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")

    init {
        refresh()
    }


    private fun refresh(){
        viewModelScope.launch {
            loadingState=true
            try {
                data.value = Firebase.firestore
                    .collection("Courses")
                    .document(OnBoardViewModel.currentUser.value.tutList)
                    .get()
                    .await().toObject<CourseData>()?:data.value
                loadingState=false
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)
                loadingState=false
                errorState=true
                errorMsg="Ouch!!! Unexpected error check your connection and try again"
            }
        }
    }


    fun updateUserState(){
        viewModelScope.launch {
            try {
                loadingState =true
                println("updating user state ")
                println("Updaing user state ${OnBoardViewModel.currentUser.value.online}")
                Firebase
                    .firestore
                    .collection("Users")
                    .document(OnBoardViewModel.currentUser.value.uid)
                    .set(OnBoardViewModel.currentUser.value).await()
                OnBoardViewModel.currentUser.value=Firebase
                    .firestore
                    .collection("Users")
                    .document(OnBoardViewModel.currentUser.value.uid)
                    .get()
                    .await().toObject<User>()?:OnBoardViewModel.currentUser.value
                println("updating user state ")
                println("Updaing user state ${OnBoardViewModel.currentUser.value.online}")
                loadingState = false
            }catch (e:Exception){
                loadingState=false
                errorState = true
                errorMsg = "Ouch!!! unexpected error happen when updating your profile state please check your connection and try again "
                e.printStackTrace()
                println(e.message)
            }
        }
    }
}