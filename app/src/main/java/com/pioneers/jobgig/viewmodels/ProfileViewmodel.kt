package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import com.pioneers.jobgig.dataobj.utils.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileViewmodel:ViewModel() {
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")
    private val storage = Firebase.storage.reference.child("UserProfile").child(Firebase.auth.currentUser?.uid?:OnBoardViewModel.currentUser.value.uid)
    private val db = Firebase.firestore.collection("Users")
    var name by mutableStateOf(OnBoardViewModel.currentUser.value .fullname)
    var about by mutableStateOf(OnBoardViewModel.currentUser.value .description)
    var address by mutableStateOf(OnBoardViewModel.currentUser.value .address)
    var profileUri by mutableStateOf("")
    var tempuri by mutableStateOf(OnBoardViewModel.currentUser.value .profilePic)
    private var stream:InputStream? = null



    fun saveProfile(ctx:Context){
       try {
           viewModelScope.launch {
               try {
                   loadingState = true
                   println("level 1")
                   if(profileUri.isNotBlank()){
                       stream = ctx.contentResolver.openInputStream(Uri.parse(profileUri))
                       if (stream == null)return@launch
                       println("level 2")
                       storage.putStream(stream!!).await()
                       println("level 3")

                       println("level 4")
                       OnBoardViewModel.currentUser.value.profilePic = storage.downloadUrl.await().toString()
                   }
                   if(name != OnBoardViewModel.currentUser.value.fullname){
                       OnBoardViewModel.currentUser.value.description = about
                   }
                   if(about != OnBoardViewModel.currentUser.value.description){
                       OnBoardViewModel.currentUser.value.description = about
                   }
                   if(address != OnBoardViewModel.currentUser.value.address){
                       OnBoardViewModel.currentUser.value.address = address
                   }
                   println("level 5")
                   db.document(Firebase.auth.currentUser?.uid?:OnBoardViewModel.currentUser.value.uid).set(OnBoardViewModel.currentUser.value).await()
                   println("level 6")
                   OnBoardViewModel.currentUser.value = db.document(Firebase.auth.currentUser?.uid?:OnBoardViewModel.currentUser.value.uid).get().await().toObject<User>()?:OnBoardViewModel.currentUser.value
                   println("level 7")
                   loadingState = false
               } catch (e:Exception){
                   e.printStackTrace()
                   println(e.message)
                   loadingState = false
                   errorState  = true
                   errorMsg ="ouch!!! unexpected error happen check your internet connection and try again please"
               }
           }
       }catch (e:Exception){
           e.printStackTrace()
           println(e.message)
       }

    }

    override fun onCleared() {
        stream?.close()
        super.onCleared()
    }
}