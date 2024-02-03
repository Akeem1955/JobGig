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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.FileNotFoundException
import java.io.InputStream

class ProfileViewmodel:ViewModel() {
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")
    private val storage = Firebase.storage.getReference("UserProfile").child(OnBoardViewModel.currentUser.uid)
    private val db = Firebase.firestore.collection("Users")
    var name by mutableStateOf(OnBoardViewModel.currentUser.fullname)
    var about by mutableStateOf(OnBoardViewModel.currentUser.description)
    var address by mutableStateOf(OnBoardViewModel.currentUser.address)
    var profileUri by mutableStateOf(OnBoardViewModel.currentUser.profilePic)
    private var stream:InputStream? = null


    fun saveProfile(ctx:Context){
        viewModelScope.launch {
            try {
               if(profileUri.isNotBlank()){
                   stream = ctx.contentResolver.openInputStream(Uri.parse(profileUri))
                   if (stream == null)return@launch
                   val result = async { storage.putStream(stream!!).await() }
                   val profile = async { storage.downloadUrl.await()}
                   result.await()
                   OnBoardViewModel.currentUser.profilePic = profile.await().toString()
               }
               if(name != OnBoardViewModel.currentUser.fullname){
                   OnBoardViewModel.currentUser.description = about
               }
                if(about != OnBoardViewModel.currentUser.description){
                    OnBoardViewModel.currentUser.description = about
                }
                if(address != OnBoardViewModel.currentUser.address){
                    OnBoardViewModel.currentUser.address = address
                }
                db.document(Firebase.auth.currentUser?.uid?:OnBoardViewModel.currentUser.uid).set(OnBoardViewModel.currentUser).await()
                OnBoardViewModel.currentUser = db.document(Firebase.auth.currentUser?.uid?:OnBoardViewModel.currentUser.uid).get().await().toObject<User>()?:OnBoardViewModel.currentUser
                loadingState = false
            }catch (e:FileNotFoundException){
                e.printStackTrace()
                println(e.message)
                loadingState= false
                errorState = true
                errorMsg = "File not found try picking another one"
            }
            catch (e:Exception){
                e.printStackTrace()
                println(e.message)
                loadingState = false
                errorState  = true
                errorMsg ="ouch!!! unexpected error happen check your internet connection and try again please"
            }
        }

    }

    override fun onCleared() {
        stream?.close()
        super.onCleared()
    }
}