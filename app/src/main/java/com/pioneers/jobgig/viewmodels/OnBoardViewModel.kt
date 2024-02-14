package com.pioneers.jobgig.viewmodels

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.dataobj.utils.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OnBoardViewModel:ViewModel() {
    companion object{
        var currentUser = mutableStateOf(User())

    }
    fun updateUser(user:User){
        currentUser.value = user
    }

    private var email by mutableStateOf("")
    private var password by mutableStateOf("")
    private var fullname by mutableStateOf("")
    private var errorEmail by mutableStateOf("")
    private var errorName by mutableStateOf("")
    private var errorPassword by mutableStateOf("")
    private var isError by mutableStateOf(false)
    private var isErrorP by mutableStateOf(false)
    private var isErrorN by mutableStateOf(false)
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
    val _isErrorN
        get() = isErrorN
    val _errorName
        get() = errorName

    fun updateEmail(update:String){
        errorEmail = ""
        isError = false
        email = update.trim()
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
                    if (user != null){
                        val nullable = Firebase.firestore.collection("Users").document(user.uid).get().await().toObject<User>()
                        if (nullable != null){
                            currentUser.value = nullable
                            sucess = true
                        }
                    }
                }
            }catch (e:Exception){
                isLoading = false
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
        if(fullname.isBlank()){
            errorName = "Your Fullname is required...."
            isErrorN = true
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
                return@launch
            }
            try {
                user?.updateProfile(UserProfileChangeRequest.Builder()
                    .setDisplayName(fullname)
                    .setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/UserProfile%2Fmingcute_user-4-fill.svg?alt=media&token=30a172c5-3106-418b-b223-e6b13680d162"))
                    .build())?.await()
                if (user != null) {
                    val userObj = User(profilePic = user.photoUrl.toString(), fullname = user.displayName?:fullname, uid = user.uid)
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