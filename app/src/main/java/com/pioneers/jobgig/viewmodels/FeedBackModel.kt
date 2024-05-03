package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.pioneers.jobgig.BuildConfig
import com.pioneers.jobgig.screens.Sentiment
import com.pioneers.jobgig.screens.promptC
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.json.Json
import java.util.Locale

class FeedBackModel : ViewModel() {
    val feedbackShowed = mutableStateOf(false)
    val showFeedbackDialog = mutableStateOf(false)
    val trendDb = Firebase.firestore.collection("Trends")
        .document("feedback")//.update("",FieldValue.arrayUnion())
    val exceptionHAndler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(throwable.message)

    }

    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-1.5-pro-latest",
        systemInstruction = Content("system", listOf(TextPart(promptC))),
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.gemini
    )


    fun submitFeedback(comment: String, liked: String, improved: String, feature: String) {
        feedbackShowed.value = true
        viewModelScope.launch(exceptionHAndler) {
            showFeedbackDialog.value = false
            val result = generativeModel.generateContent(comment).text ?: ""
            val sentiment = Json.decodeFromString<Sentiment>(result)
            trendDb.update("needImprove", FieldValue.arrayUnion(improved)).await()
            trendDb.update("liked_feature", FieldValue.arrayUnion(liked)).await()
            trendDb.update("features", FieldValue.arrayUnion(feature)).await()
            if (sentiment.sentiment.lowercase(Locale.ENGLISH) == "good") {
                trendDb.update("appGood", FieldValue.increment(1)).await()
            } else if (sentiment.sentiment.lowercase(Locale.ENGLISH) == "buggy") {
                trendDb.update("appBuggy", FieldValue.increment(1)).await()
            } else {
                trendDb.update("neutral", FieldValue.increment(1)).await()
            }


        }
    }
}