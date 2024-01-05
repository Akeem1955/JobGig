package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OnBoardViewModel:ViewModel() {
    private var email by mutableStateOf("")
    private var password by mutableStateOf("")
    private var fullname by mutableStateOf("")
    private var errorEmail by mutableStateOf("")
    private var errorPassword by mutableStateOf("")
    private var isError by mutableStateOf(false)
    private var isErrorP by mutableStateOf(false)
    private var isLoading by mutableStateOf(false)


    val _email
        get() = email
    val _password
        get() = password
    val  _fullname
        get() = fullname
    val _isLoading
        get() = isLoading
    val _errorEmail
        get() = errorEmail
    val _errorPassword
        get() = errorPassword
    val _isError
        get() = isError
    val _isErrorP
        get() = isErrorP

    fun updateEmail(update:String){
        errorEmail = ""
        isError = false
        email = update
    }
    fun updatePassword(update: String){
        errorPassword = ""
        isErrorP = false
        password = update
    }
    fun updateFullname(update: String){
        fullname = update
    }
    fun loadState(load:Boolean){
        isLoading = load
    }



    fun login(){
        viewModelScope.launch {
            try {
                val result =Firebase.auth.signInWithEmailAndPassword(email, password).await()
                val user = result.user
                if(user?.isEmailVerified == false){
                    user.sendEmailVerification()
                }
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

    fun signup(){
        viewModelScope.launch {
            try {
                val result =Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                if(user?.isEmailVerified == false){
                    user.sendEmailVerification()
                }
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
    fun reset(){
        viewModelScope.launch {
            try {
                Firebase.auth.sendPasswordResetEmail("adetunjiazeem2004@gmail.com").await()
            }catch (e:Exception){}
        }
    }

}