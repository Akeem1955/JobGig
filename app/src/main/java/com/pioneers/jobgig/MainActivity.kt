package com.pioneers.jobgig


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.dataStore
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.pioneers.jobgig.screens.HomeScreen
import com.pioneers.jobgig.screens.Screen1
import com.pioneers.jobgig.services.preference.PreferenceSerializer
import com.pioneers.jobgig.ui.theme.JobGigTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    val db = Firebase.firestore.collection("Courses")



    val LocalHost = compositionLocalOf { SnackbarHostState() }
    //val Context.datastore by dataStore("app_preference.json",PreferenceSerializer)

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
            statusBarStyle = SystemBarStyle.light(scrim = android.graphics.Color.TRANSPARENT, darkScrim = android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(scrim = android.graphics.Color.TRANSPARENT, darkScrim = android.graphics.Color.TRANSPARENT)
        )


//        Authenticate()
        setContent {
            JobGigTheme {
//                Box {
//                    CompositionLocalProvider(LocalHost provides LocalHost.current) {
//                        HomeScreen()
//                    }
//                }
                val string = listOf(
                    "Plumbing,#F2EAD5",
                    "Catering_and_Culinary_Arts,E9A9C4",
                    "Carpentry,#D4CBFB",
                    "Salon_and_Beauty Services,#F9C4C4",
                    "Electrical_Installation,#E0F4DB",
                    "Automotive_Repair,#C4DCF9",
                    "Masonry_and_Bricklaying,#D4CBFB",
                    "Welding_and_Metal_Fabrication,#F2EAD5",
                    "Tailoring_and_Fashion_Design,#C4E6F9",
                    "Landscaping_and_Gardening,#E7F9C4",
                    "Air_Conditioning_Maintenance,#A7B4D8",
                    "Painting_and_Decorating,#FFF6E2",
                    "Jewelry_Making_and_Metalworking,#E9E4E4",
                    "Appliance_Repair,#E6E8AA",
                    "Upholstery_and_Furniture_Restoration,#5998D2",
                    "Knitting_and_Textile_Crafts,#72DB62",
                    "Photography,#A7B4D8",
                    "Fabric_Dyeing_and_Tie-Dye_Techniques,#C199E8",
                    "Aluminium_Fabrication_and_Fitting,#E9E4E4"
                )
//                var uri by remember {
//                    mutableStateOf("")
//                }
                val scope = rememberCoroutineScope()
                Box (modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(android.graphics.Color.parseColor("#F2EAD5")))){
                   Button(modifier = Modifier.align(Alignment.Center),onClick = { scope.launch {
                       try {
                           println(GetCourseDatas()?.courses == null)
                           GetCourseDatas()?.courses?.forEach {
                               println(it.imageUri)
                               async {
                                   db.document("{${it.title}${Timestamp.now()}").set(it).await()
                               }
                           }
                       }catch (e:Exception){
                           println(e.printStackTrace())
                           println("Exception in firestore ${e.message}")
                       }
                   } }) {
                       Text(text = "Upload Please", color = Color.White, fontWeight = FontWeight.Bold)
                   }
                }
                //ScreenNav(navHostController = rememberNavController())
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
