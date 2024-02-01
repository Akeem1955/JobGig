package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.UUID

class VocViewmodel:ViewModel() {
    val db = Firebase.storage.reference.child("Pastwork")
    val dbB = Firebase.firestore
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var about = mutableStateOf("")
    var address = mutableStateOf("")
    var videoUri = mutableStateOf("")
    var gallery = emptyList<String>()
    var streams = emptyList<InputStream>()
    var stream:InputStream? = null
    var downloadurls= mutableListOf<StorageReference>()





    fun requestVerification(ctx:Context){
        viewModelScope.launch {
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
                OnBoardViewModel.currentUser.imageOfPrevWork = downloadurls.map {
                    async { it.downloadUrl.await().toString() }
                }.map { it.await()}
                OnBoardViewModel.currentUser.email=email.value
                OnBoardViewModel.currentUser.address=address.value
                OnBoardViewModel.currentUser.phone=phone.value
                OnBoardViewModel.currentUser.description=about.value

            }catch (e:Exception){
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
