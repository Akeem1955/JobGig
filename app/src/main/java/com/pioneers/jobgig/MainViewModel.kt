package com.pioneers.jobgig

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.MultiFactorSession
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.PhoneMultiFactorGenerator
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

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