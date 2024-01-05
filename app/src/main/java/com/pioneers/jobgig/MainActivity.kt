package com.pioneers.jobgig

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.pioneers.jobgig.screens.Login
import com.pioneers.jobgig.screens.ScreenNav
import com.pioneers.jobgig.ui.theme.JobGigTheme

class MainActivity : ComponentActivity() {
    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult?) {
        val response = result?.idpResponse
        if (result != null) {
            if (result.resultCode == RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                println(user.toString())
                println(user?.email)
            } else {
                println("Error Happened")
            }
        }

    }


   // @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(scrim = Color.TRANSPARENT, darkScrim = Color.TRANSPARENT)
        )


//        Authenticate()
        setContent {
            JobGigTheme {
                ScreenNav(navHostController = rememberNavController())
//                Scaffold {
//                    EnrollCourse(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding(), title = "Learn The Basics About Canon Eos", duration = "12h 45min", ratings = 4.3, studentEnrolled =3598 , numRating =400 )
//                }
//                LaunchedEffect(key1 = true){
//                    Authenticate()
//                }
                // A surface container using the 'background' color from the theme
//                Scaffold {
//                    Column(modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.Green)) {
//                        //EnrollCourse(it.calculateTopPadding(), it.calculateBottomPadding())
//                    }
//                }
//                Scaffold(modifier = Modifier.fillMaxSize()) {
//                    EnrollCourse(top = it.calculateTopPadding(),  bottom = it.calculateBottomPadding())
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
