package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.pioneers.jobgig.dataobj.utils.Category
import com.pioneers.jobgig.dataobj.utils.CategoryItems
import com.pioneers.jobgig.dataobj.utils.CourseContent
import com.pioneers.jobgig.dataobj.utils.CourseData
import com.pioneers.jobgig.services.preference.AppPreference
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CourseViewModel:ViewModel() {
    private var searchQueryFinish = false
    private var popularQueryFinished = false
    private val db = Firebase.firestore
    private val topCategory =db.collection("Courses").document("top_category")
    private var popularQuery = db.collection("Courses").orderBy("learners",Query.Direction.DESCENDING).limit(20)
    private var popularQuery_x = db.collection("Courses").orderBy("learners",Query.Direction.DESCENDING).limit(20)
    private var searchQuery = db.collection("Courses").limit(20).orderBy("title")
    private var searchQuery_x = db.collection("Courses").limit(20).orderBy("title")
    private var popularCourseSnapshot = MutableStateFlow(mutableListOf<DocumentSnapshot>())
    private var searchCourseSnapshot = MutableStateFlow(mutableListOf<DocumentSnapshot>())
    var searchResultCourse = derivedStateOf {
        searchCourseSnapshot.value.map {doc->
            doc.toObject(CourseData::class.java)
        }
    }
        private set
    private var popularCourses = derivedStateOf { popularCourseSnapshot.value.map {doc->
        doc.toObject(CourseData::class.java)

    } }
    var topCategoryCourse  by mutableStateOf(emptyList<Category>())
        private set
    var loadingState by mutableStateOf(true)
        private set
    private var player: ExoPlayer? = null
    val popularCourse
        get() = popularCourses
    val playeR
        get() = player




    init {
        viewModelScope.launch {
            try {
                val categoryResponse = async {
                    topCategory.get().await()
                }
                val popularResponse = async {
                    popularQuery.get().await()
                }
                topCategoryCourse = categoryResponse.await().toObject(CategoryItems::class.java)?.categories?.sortedByDescending {it.userEnrolled} ?: topCategoryCourse
                val docs = popularResponse.await().documents
                if(docs.size < 20){
                    popularQueryFinished  = true
                }
                popularCourseSnapshot.value.addAll(docs)
                loadingState = false
            }catch (e:Exception){
                println(e.message)
                loadingState = false
            }
        }
    }

    private fun playerInit(context: Context):ExoPlayer{
        if (player != null)
            return player as ExoPlayer
        else
            player = ExoPlayer.Builder(context).build()
        return player as ExoPlayer
    }

    fun init(ctx:Context): CourseViewModel {
        return if (player != null){
            this
        } else{
            playerInit(ctx)
            this
        }
    }

    fun updateRecentSearches(datastore: DataStore<AppPreference>, query:String){
        viewModelScope.launch {
            try {
                datastore.updateData {
                    it.copy(
                        searches = it.searches.mutate {s->
                            s.add(query)
                        }
                    )
                }
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun getSearchItem(query:String){
        loadingState = true
       viewModelScope.launch {
           searchQuery = searchQuery_x.startAt(query).endAt(query+"\uf8ff")
           val searchResponse = async {
               searchQuery.get().await()
           }
           val docs = searchResponse.await().documents
           if(docs.size < 20){
               searchQueryFinish = true
           }
           searchCourseSnapshot.value.addAll(docs)
       }
    }
    fun loadMoreSearchQuery(){
        loadingState = true
        if (searchQueryFinish){
            loadingState = false
            return
        }
        viewModelScope.launch {
            searchQuery = searchQuery.startAfter(searchCourseSnapshot.value.last())
            try {
                val searchResponse = async {
                    searchQuery.get().await()
                }
                val docs = searchResponse.await().documents
                if(docs.size < 20){
                    searchQueryFinish = true
                }
                searchCourseSnapshot.value.addAll(docs)
            }catch (e:Exception){
                e.printStackTrace()
                println(e.message)

            }
        }

    }
    fun loadMorePopularQuery(){
        loadingState = true
        if (popularQueryFinished){
            loadingState = false
            return
        }
        viewModelScope.launch {
            try {
                val popularResponse = async {
                    popularQuery.startAfter(popularCourseSnapshot.value.last()).get().await()
                }
                val docs = popularResponse.await().documents
                if(docs.size < 20){
                    popularQueryFinished = true
                }
                popularCourseSnapshot.value.addAll(docs)
                loadingState = false
            }catch (e:Exception){
                loadingState = false
                e.printStackTrace()
                println(e.message)

            }
        }

    }


    fun addMediaItems(courseContent: List<CourseContent>){
        player?.setMediaItems(courseContent.map {
            it.uri
        }.map {uri->
            MediaItem.fromUri(uri)
        })
        player?.prepare()
    }
    fun playVideo(index:Int){
        player?.seekTo(index,0)
    }


    override fun onCleared() {
        player?.release()
    }
}