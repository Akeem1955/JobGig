package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.pioneers.jobgig.dataobj.utils.Trend
import com.pioneers.jobgig.dataobj.utils.Trends
import com.pioneers.jobgig.screens.BarData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TrendModel : ViewModel() {
    val trendDb = Firebase.firestore.collection("Trends")
        .document("feedback")//.update("",FieldValue.arrayUnion())
    val trendDb2 = Firebase.firestore.collection("Trends").document("UDv3PHztPqAw6vGoCUjq")
    val trendState = mutableStateOf(listOf<Trend>())
    var bardataA = mutableStateOf(listOf<BarData>())
    var bardataB = mutableStateOf(listOf<BarData>())
    var bardataC = mutableStateOf(listOf<BarData>())
    val exceptionHAndler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.message)

    }


    init {
        getDataTrends()
    }


    fun getTrends() {
        viewModelScope.launch {

        }
    }

    fun getDataTrends() {
        viewModelScope.launch(exceptionHAndler) {
            trendState.value = trendDb2.get().await().toObject<Trends>()?.trends ?: emptyList()
            bardataA.value = trendState.value.map {
                BarData(it.hired_daily, it.skill)
            }
            bardataB.value = trendState.value.map {
                BarData(it.average_pay, it.skill)
            }
            bardataC.value = trendState.value.map {
                BarData(it.average_time, it.skill)
            }

        }
    }
}