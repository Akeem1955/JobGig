package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.pioneers.jobgig.dataobj.utils.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.UUID

class VocViewmodel:ViewModel() {
    val db = Firebase.storage.reference.child("Pastwork")
    val dbB = Firebase.firestore.collection("Users")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var about = mutableStateOf("")
    var address = mutableStateOf("")
    var videoUri = mutableStateOf("")
    var gallery = emptyList<String>()
    var streams = emptyList<InputStream>()
    var stream:InputStream? = null
    var downloadurls= mutableListOf<StorageReference>()
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")





    fun requestVerification(ctx:Context){
        viewModelScope.launch {
            loadingState = true
            try {
                val vids =db.child(UUID.randomUUID().toString())
                val vidres = async { ctx.contentResolver.openInputStream(Uri.parse(videoUri.value))
                    ?.let {vids.putStream(it) } }
               streams = gallery.mapNotNull{
                    ctx.contentResolver.openInputStream(Uri.parse(it))
                }
               val responses = streams.map {
                   val uuid = db.child(UUID.randomUUID().toString())
                   downloadurls.add(uuid)
                   async { uuid.putStream(it).await()}
               }
                responses.forEach { it.await()}
                vidres.await()
                OnBoardViewModel.currentUser.introVideo=vids.downloadUrl.await().toString()
                OnBoardViewModel.currentUser.imageOfPrevWork = downloadurls.map {
                    async { it.downloadUrl.await().toString() }
                }.map { it.await()}
                OnBoardViewModel.currentUser.email=email.value
                OnBoardViewModel.currentUser.address=address.value
                OnBoardViewModel.currentUser.phone=phone.value
                OnBoardViewModel.currentUser.description=about.value
                OnBoardViewModel.currentUser.verified = true
                dbB.document(OnBoardViewModel.currentUser.uid).set(OnBoardViewModel.currentUser).await()
                OnBoardViewModel.currentUser = dbB.document(OnBoardViewModel.currentUser.uid).get().await().toObject<User>() ?:OnBoardViewModel.currentUser
                loadingState=false
            }catch (e:Exception){
                loadingState=false
                errorState=true
                errorMsg="Ouch!!! Unexpected error check your connection and retry..."
                e.printStackTrace()
                println(e.message)
            }
        }
    }


    override fun onCleared() {
        stream?.close()
        streams.forEach {
            it.close()
        }
        super.onCleared()
    }
}
