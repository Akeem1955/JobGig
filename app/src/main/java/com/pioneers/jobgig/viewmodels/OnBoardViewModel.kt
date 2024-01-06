package com.pioneers.jobgig.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.dataobj.User
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
    var shouldShowEmailVerifyLogin by mutableStateOf(false)
    var shouldShowEmailVerifySignUp by mutableStateOf(false)
    var sucess by mutableStateOf(false)
        private set
    var sucessB by mutableStateOf(false)
        private set
    var sucessC by mutableStateOf(false)
        private set


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
    fun clearInfos(){
        email = ""
        password = ""
        errorEmail =""
        errorPassword =""
        isError = false
        isErrorP = false
        isLoading = false

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
        isLoading = true
        viewModelScope.launch {
            try {
                val result =Firebase.auth.signInWithEmailAndPassword(email, password).await()
                println("we reach here")
                val user = result.user
                if(user?.isEmailVerified == false){
                    user.sendEmailVerification()
                    isLoading = false
                    shouldShowEmailVerifyLogin = true
                }else{
                    sucess = true
                }
            }catch (e:Exception){
                isError = true
                errorEmail = e.message ?:""
                println(e.message)
            }
        }
    }

    fun signup(){
        val reg:Regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$]).{8,}$")
        if(!password.matches(reg)){
            errorPassword = "Password Must Contain Uppercase, Number , and Special charcater(@#$)"
            isErrorP = true
            return
        }
        isLoading = true
        viewModelScope.launch {
            var user: FirebaseUser? = null
            try {
                val result =Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                user = result.user
                if(user?.isEmailVerified == false){
                    user.sendEmailVerification()
                    isLoading = false
                    shouldShowEmailVerifySignUp = true
                }
            }catch (e:Exception){
                isLoading = false
                isError = true
                errorEmail = e.message ?:""
            }
            try {
                val userObj =User(null,0.0,null,false, emptyList(), emptyList(), emptyList(),false,null)
                if (user != null) {
                    Firebase.firestore.collection("Users").document(user.uid).set(userObj).await()
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
            }catch (e:Exception){
                println(e.message)
            }
        }
    }

}