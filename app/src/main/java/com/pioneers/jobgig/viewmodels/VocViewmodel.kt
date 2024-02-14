package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.ScreenRoute
import com.pioneers.jobgig.screens.VocationalCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.InputStream
import java.util.UUID

class VocViewmodel:ViewModel() {
    val db = Firebase.storage.reference.child("Pastwork")
    val dbB = Firebase.firestore.collection("Users")
    var type = mutableStateOf(VocationalCategory.Salon.name)
    var phone = mutableStateOf("")
    var about = mutableStateOf("")
    var address = mutableStateOf("")
    var videoUri = mutableStateOf("")
    var gallery by mutableStateOf(emptyList<String>())
    var streams = emptyList<InputStream>()
    var stream:InputStream? = null
    var downloadurls= mutableListOf<StorageReference>()
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")





    fun requestVerification(ctx:Context,navController: NavController){
        if (phone.value.isBlank()){
            errorState = true
            errorMsg = "phone number is required..."
            return
        }
        if (about.value.isBlank()){
            errorState = true
            errorMsg = "about yourself is required..."
            return
        }
        if (address.value.isBlank()){
            errorState = true
            errorMsg = "address is required..."
            return
        }
        if (videoUri.value.isBlank()){
            errorState = true
            errorMsg = "30s of your work is required.."
            return
        }
        if (type.value.isBlank()){
            errorState = true
            errorMsg = "vocational type is required"
            return
        }
        if (gallery.isEmpty()){
            errorState = true
            errorMsg = "at-least upload one of your past work image"
            return
        }
        viewModelScope.launch {
            loadingState = true
            try {
                val vids =db.child(UUID.randomUUID().toString())
                try {
                    stream = ctx.contentResolver.openInputStream(Uri.parse(videoUri.value))
                }catch (e:Exception){e.printStackTrace()}
                val vidres = async {stream
                    ?.let {vids.putStream(it).await().storage.downloadUrl.await().toString() } }
                println("level 1")
               try {
                   streams = gallery.mapNotNull{
                       ctx.contentResolver.openInputStream(Uri.parse(it))
                   }
               }catch (e:Exception){e.printStackTrace()}
               val responses = streams.map {
                   val uuid = db.child(UUID.randomUUID().toString())
                   async { uuid.putStream(it).await().storage.downloadUrl.await().toString()}
               }
                println("level 2")
                responses.awaitAll()
                println("level 3")
                vidres.await()
                println("level 4")
                OnBoardViewModel.currentUser.value.introVideo=vidres.await()?:""
                println("level 5")
                OnBoardViewModel.currentUser.value.imageOfPrevWork =responses.awaitAll()
                println("level 6")
                OnBoardViewModel.currentUser.value.email=Firebase.auth.currentUser?.email?:""
                OnBoardViewModel.currentUser.value.address=address.value
                OnBoardViewModel.currentUser.value.phone=phone.value
                OnBoardViewModel.currentUser.value.description=about.value
                OnBoardViewModel.currentUser.value.vocationalType = type.value
                OnBoardViewModel.currentUser.value.verified = true
                dbB.document(OnBoardViewModel.currentUser.value.uid).set(OnBoardViewModel.currentUser.value).await()
                OnBoardViewModel.currentUser.value = dbB.document(OnBoardViewModel.currentUser.value.uid).get().await().toObject<User>() ?:OnBoardViewModel.currentUser.value
                loadingState=false
                navController.navigate(ScreenRoute.HomeEntry.route){
                    popUpTo(ScreenRoute.HomeEntry.route){
                        inclusive = true
                    }
                }
            }catch (e:Exception){
                loadingState=false
                errorState=true
                errorMsg="${e.message}, ${e.stackTrace.toList()}"
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
