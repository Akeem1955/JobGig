package com.pioneers.jobgig.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.ImagePart
import com.google.ai.client.generativeai.type.TextPart
import com.pioneers.jobgig.BuildConfig
import com.pioneers.jobgig.screens.ChatData
import com.pioneers.jobgig.screens.ChatType
import com.pioneers.jobgig.screens.prompt
import com.pioneers.jobgig.screens.promptB
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.InputStream

class VocaSageModel : ViewModel() {
    val exceptionHAndler = CoroutineExceptionHandler { coroutineContext, throwable ->
        isLoading.value = false
        println(throwable.message)
        println("error happen when generating reply AI")

    }
    val generativeModel = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-1.5-pro-latest",
        systemInstruction = Content("system", listOf(TextPart(promptB))),
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.gemini
    )
    val generativeModelB = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-1.5-pro-latest",
        systemInstruction = Content("system", listOf(TextPart(promptB))),
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.gemini
    )
    val generativeModelC = GenerativeModel(
        // For text-only input, use the gemini-pro model
        modelName = "gemini-1.5-pro-latest",
        systemInstruction = Content("system", listOf(TextPart(prompt))),
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.gemini
    )


    val chatType = mutableStateOf(ChatType.Normal)
    val isActive = mutableStateOf(false)
    private var uri: Uri = "".toUri()
    val _uri
        get() = uri

    fun setUri(uris: Uri) {
        uri = uris
    }

    val promptState = mutableStateOf("")
    var chatHistory by mutableStateOf(listOf<Content>())
        private set
    var chatData by mutableStateOf(listOf<ChatData>())
        private set
    private var stream: InputStream? = null


    val isLoading = mutableStateOf(false)
    val replyState = mutableStateOf("")
    fun generateContent() {
        viewModelScope.launch(exceptionHAndler) {
            isLoading.value = true
            chatData = chatData + ChatData(role = "user", promptState.value)
            val contentUser = Content(role = "user", parts = listOf(TextPart(promptState.value)))
            val temp = promptState.value
            promptState.value = ""
            val result = generativeModel.startChat(chatHistory).sendMessage(temp)
            replyState.value = result.text ?: replyState.value
            if (result.text != null) {
                val contentBot = Content(role = "model", parts = listOf(TextPart(replyState.value)))
                chatHistory = chatHistory + contentUser
                chatHistory = chatHistory + contentBot
                chatData = chatData + ChatData(role = "model", replyState.value)
            }
            isLoading.value = false
            println(replyState.value)
        }
    }

    fun generateContentB() {
        viewModelScope.launch(exceptionHAndler) {
            isLoading.value = true
            chatData = chatData + ChatData(role = "user", promptState.value)
            val contentUser = Content(role = "user", parts = listOf(TextPart(promptState.value)))
            val temp = promptState.value
            promptState.value = ""
            val result = generativeModelC.startChat(chatHistory).sendMessage(temp)
            replyState.value = result.text ?: replyState.value
            if (result.text != null) {
                val contentBot = Content(role = "model", parts = listOf(TextPart(replyState.value)))
                chatHistory = chatHistory + contentUser
                chatHistory = chatHistory + contentBot
                chatData = chatData + ChatData(role = "model", replyState.value)
            }
            isLoading.value = false
            println(replyState.value)
        }
    }

    fun generateContentC(ctx: Context, text: String, bitmaps: Bitmap) {
        viewModelScope.launch(exceptionHAndler) {
            chatData = chatData + ChatData(role = "user", text)
            if (uri.toString() != "") {
                stream = ctx.contentResolver.openInputStream(uri)
            }
            val bitmap = BitmapFactory.decodeStream(stream) ?: bitmaps
            isLoading.value = true
            val result = generativeModelB.generateContent(
                Content(
                    "user",
                    listOf(TextPart(text), ImagePart(bitmap))
                )
            )
            replyState.value = result.text ?: replyState.value
            if (result.text != null) {
                chatData = chatData + ChatData(role = "model", replyState.value)
            }
            isLoading.value = false
            println(replyState.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stream?.close()
    }

}
