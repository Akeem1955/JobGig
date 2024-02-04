package com.pioneers.jobgig

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel: ViewModel() {
    private var auth = Firebase.auth
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    init {
     println("View Model Created")
    }

    fun Initiate(){
    }
    fun authenticate(){
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email,password).await()
                val user = result.user
                println(result.user?.email)
                if(user?.isEmailVerified == false){
                    user.sendEmailVerification()
                }
            }catch (e:Exception){
                println(e.message)
            }
        }
    }
//    fun Authenticate(launcher: ActivityResultLauncher<Intent>){
//        // Choose authentication providers
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.GoogleBuilder().build())
//
//// Create and launch sign-in intent
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            .build()
//        launcher.launch(signInIntent)
//    }
//
//    suspend fun multifactor(user:FirebaseUser, activity: MainActivity){
//        try {
//            val session = user.multiFactor.session.await()
//            val callback = object : OnVerificationStateChangedCallbacks(){
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                    viewModelScope.launch { println("Your Sms Code is ${p0.smsCode}") }
//                }
//
//                override fun onVerificationFailed(p0: FirebaseException) {
//                    println("Error occured${p0.message}")
//                }
//
//                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
//                    super.onCodeSent(p0, p1)
//                }
//            }
//            val phoneAuthOptions = PhoneAuthOptions.newBuilder()
//                .setPhoneNumber("+2349064805505")
//                .setTimeout(30L, TimeUnit.SECONDS)
//                .setMultiFactorSession(session)
//                .setCallbacks(callback)
//                .setActivity(activity)
//                .requireSmsValidation(true)
//                .build()
//            PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
//            println("Verification Sent by sms")
//        }catch (e:Exception){
//            println(e.message)
//        }
//
//    }
}