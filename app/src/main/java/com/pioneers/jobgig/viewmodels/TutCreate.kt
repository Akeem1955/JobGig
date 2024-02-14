package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import com.pioneers.jobgig.dataobj.utils.CourseContent
import com.pioneers.jobgig.dataobj.utils.CourseData
import com.pioneers.jobgig.dataobj.utils.InstructorDesignData
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.ScreenRoute
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TutCreate:ViewModel() {
    var requirement = mutableStateListOf<String>()
    var who = mutableStateListOf<String>()
    var aim = mutableStateListOf<String>()
    var tutContent = mutableStateListOf<CourseContent>()
    var tutName = mutableStateOf("")
    var tutDes = mutableStateOf("")
    var about = mutableStateOf("")
    var thumburi =mutableStateOf("")
    var loadingState by mutableStateOf(false)
    var errorState by mutableStateOf(false)
    var errorMsg by mutableStateOf("")
    private val db = Firebase.firestore.collection("Courses")
    private val storage =Firebase.storage.reference.child("CourseVideo")
    private val storageNail =Firebase.storage.reference.child("CourseImage")
    private val users = Firebase.firestore.collection("Users")

    fun createTut(ctx:Context,navController: NavController){
        loadingState = true
        viewModelScope.launch {
            if (thumburi.value.isBlank()){
                errorState=true
                errorMsg = "Tutorial Thumbnail required..."
                return@launch
            }
            if (tutName.value.isBlank()||tutDes.value.isBlank()){
                errorState=true
                errorMsg = "Tutorial name and description are required..."
                return@launch
            }
            if (requirement.isEmpty()||who.isEmpty()||aim.isEmpty()){
                errorState=false
                errorMsg = "Tutorial requirement, aim, and for who is required please..."
                return@launch
            }
            if (tutContent.isEmpty()){
                errorState=true
                errorMsg = "Add at-least one tutorial content please..."
                return@launch
            }
            try {
                val tut = CourseData()
                val tutVideo = tutContent.map {
                    async {
                        CourseContent(it.title,ctx.contentResolver.openInputStream(Uri.parse(it.uri))
                            ?.let { it1 -> storage.child(UUID.randomUUID().toString()).putStream(it1).await().storage.downloadUrl.await().toString()
                            }?:"")
                    }
                }.filter {
                    it.await().title.isNotBlank()
                }


                tut.imageUri = ctx.contentResolver.openInputStream(Uri.parse(thumburi.value))
                    ?.let { storageNail.child(UUID.randomUUID().toString()).putStream(it).await().storage.downloadUrl.await().toString() }
                    .toString()
                tut.requirement=requirement
                tut.description = tutDes.value
                tut.content = tutVideo.awaitAll()
                tut.forWho = who
                tut.whatLearn=aim
                tut.keyword = generateKeywords(tutName.value)
                tut.title =tutName.value
                tut.instructorInfo = listOf(InstructorDesignData(name = OnBoardViewModel.currentUser.value.fullname, uri =OnBoardViewModel.currentUser.value.profilePic , description = about.value))
                OnBoardViewModel.currentUser.value.tutCreated = true
                OnBoardViewModel.currentUser.value.tutList = db.add(tut).await().id
                users.document(OnBoardViewModel.currentUser.value.uid).set(OnBoardViewModel.currentUser.value).await()
                OnBoardViewModel.currentUser.value = users.document(OnBoardViewModel.currentUser.value.uid).get().await().toObject<User>()?:OnBoardViewModel.currentUser.value
                loadingState = false
                navController.popBackStack()
            }catch (e:Exception){
                loadingState= false
                errorMsg="Ouch!!! unexpected error check your connection and retry"
                errorState=true
                e.printStackTrace()
                println(e.message)
            }
        }
    }




    //tut upload
    val query = db.limit(20).orderBy("learners")
    private var snapshots = mutableStateOf(CourseData())

    var tutorial = snapshots.value.content.toMutableStateList().filter {
        it.title.contains("")
    }.toMutableStateList()

    var newTutUri = mutableStateOf("")
    var newTutTitle = mutableStateOf("")

    fun getTutorial(){
        viewModelScope.launch {
            try {
                loadingState=true
                println("level 1")
                snapshots.value =  db.document(OnBoardViewModel.currentUser.value.tutList).get().await().toObject<CourseData>()!!
                println("level 2")
                println("level 3")
                println(snapshots.value.content)
                println("level 4")
                tutorial.clear()
                tutorial.addAll(snapshots.value.content.toMutableStateList().filter {
                    it.title.contains("")
                }.toMutableStateList())
                println(tutorial)
                loadingState=false
            }catch (e:Exception){
                loadingState=false
                errorState=true
                errorMsg="Ouch!!! we encounter an unexpected error check your connection and retry"
                e.printStackTrace()
                println(e.message)
            }
        }
    }

    fun upload(ctx:Context){
        if (newTutUri.value.isBlank()){
            errorState=true
            errorMsg="You have not pick a file for the tutorial video"
            return
        }
        if (newTutTitle.value.isBlank()){
            errorState=true
            errorMsg="tutorial title is required..."
            return
        }
        if (snapshots.value.title.isBlank()){
            getTutorial()
            loadingState=false
            errorState=true
            errorMsg="Ouch!!! we encounter an unexpected error check your connection and retry"
            return
        }
        viewModelScope.launch {
            loadingState = true
           try {
               val newtut = CourseContent(newTutTitle.value,ctx.contentResolver.openInputStream(Uri.parse(newTutUri.value))
                   ?.let { it1 -> storage.child(UUID.randomUUID().toString()).putStream(it1).await().storage.downloadUrl.await().toString()
                   }?:"")
               if (newtut.uri.isBlank()){
                   loadingState=false
                   errorState=true
                   errorMsg="Ouch!!! we encounter an error when uploading your file check your connection and retry"
                   return@launch
               }
               snapshots.value.content = snapshots.value.content.toMutableList().also { it.add(newtut) }
               db.document(OnBoardViewModel.currentUser.value.tutList).set(snapshots.value).await()
               getTutorial()
               loadingState = false
           }catch (e:Exception){
               loadingState=false
               errorState=true
               errorMsg="Ouch!!! we encounter an unexpected error check your connection and retry"
               e.printStackTrace()
               println(e.message)
           }
        }
    }

    fun filter(query:String){
        println(snapshots.value.content)
        tutorial.clear()
        tutorial.addAll(snapshots.value.content.toMutableStateList().filter {
            it.title.contains(query)
        }.toMutableStateList())
    }

    fun delete(index:Int){
        viewModelScope.launch{
            try {
                loadingState=true
                snapshots.value.content = snapshots.value.content.toMutableList().also {it.removeAt(index)}
                db.document(OnBoardViewModel.currentUser.value.tutList).set(snapshots.value).await()
                getTutorial()
                loadingState=false
            }catch (e:Exception){
                loadingState=false
                e.printStackTrace()
                println(e.message)
                errorMsg ="Unable to delete tutorial.."
                errorState =true
            }
        }
    }

    fun generateKeywords(title:String): List<String> {
        val keyword = mutableListOf<String>()
        for (i in title.indices){
            var temp = ""
            for (j in i until title.length){
                temp += title[j]
                keyword.add(temp)
            }
        }
        return keyword
    }





}