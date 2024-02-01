package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.pioneers.jobgig.dataobj.utils.CourseContent
import kotlinx.coroutines.flow.MutableStateFlow

class TutCreate:ViewModel() {
    var requirement = mutableStateListOf<String>()
    var who = mutableStateListOf<String>()
    var aim = mutableStateListOf<String>()
    var tutContent = mutableStateListOf<CourseContent>()
    var tutName = mutableStateOf("")
    var tutDes = mutableStateOf("")
    var about = mutableStateOf("")

}