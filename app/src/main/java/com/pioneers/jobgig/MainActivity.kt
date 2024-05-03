package com.pioneers.jobgig


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.NotificationManagerCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.pioneers.jobgig.dataobj.utils.User
import com.pioneers.jobgig.screens.ScreenNav
import com.pioneers.jobgig.screens.ScreenRoute
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.OnBoardViewModel
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
//    val db = Firebase.firestore.collection("Courses")


    var keep = true

    val LocalHost = compositionLocalOf { SnackbarHostState() }


    //val Context.datastore by dataStore("app_preference.json",PreferenceSerializer)

    // See: https://developer.android.com/training/basics/intents/result




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       NotificationManagerCompat.from(applicationContext).cancel(1957)
        installSplashScreen().setKeepOnScreenCondition {keep}
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = android.graphics.Color.TRANSPARENT, darkScrim = android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(scrim = android.graphics.Color.TRANSPARENT, darkScrim = android.graphics.Color.TRANSPARENT)
        )
//       val sessionManager: WebRtcSessionManager = WebRtcSessionManagerImpl(
//           context = this,
//           signalingClient = SignalingClient(),
//           peerConnectionFactory = StreamPeerConnectionFactory(this)
//       )
        val user = Firebase.auth.currentUser
//        Authenticate()
        setContent {
            JobGigTheme {
                var loadingState by rememberSaveable {
                    mutableStateOf(false)
                }
                var retry by rememberSaveable {
                    mutableIntStateOf(0)
                }
                var errorState by rememberSaveable {
                    mutableStateOf(false)
                }
                var errorMsg by rememberSaveable {
                    mutableStateOf("")
                }
                if(loadingState){
                    Dialog(onDismissRequest = { /*TODO*/ }) {
                        CircularProgressIndicator()
                    }
                }
                if(errorState){
                    Dialog(onDismissRequest = { loadingState=true;errorState = false;retry++ }) {
                        Surface(shape = MaterialTheme.shapes.large) {
                            Text(text = errorMsg, modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
                        }
                    }
                }
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (user == null || !user.isEmailVerified){
                        keep=false
                        ScreenNav(navHostController = rememberNavController(), start = ScreenRoute.GetStarted.route )
                    }
                    else if(user.isEmailVerified){
                        LaunchedEffect(key1 = retry){
                            try {
                                loadingState=true
                                OnBoardViewModel.currentUser.value =
                                    Firebase.firestore.collection("Users").document(user.uid).get().await().toObject<User>()!!
                                loadingState =false
                            }catch (e:Exception){
                                loadingState=false
                                errorState = true
                                errorMsg = "ouch!!! unexpected error check your connection and  retry"
                                e.printStackTrace()
                                println(e.message)
                            }
                        }
                        if (!loadingState && !errorState){
                            keep=false
                            ScreenNav(navHostController = rememberNavController(), start = ScreenRoute.HomeEntry.route )
                        }
                    }
                }
//                NavHost(navController = rememberNavController(), startDestination = "alert"){
//                    composable("alert"){
//                        Simulator()
//                    }
//                    composable(deepLinks = listOf(
//                        navDeepLink {
//                            uriPattern = "jobgig://confirm-gig/{data}"
//                            action = Intent.ACTION_VIEW
//                [-]        }
//                    ),
//                        arguments = listOf(
//                            navArgument(name = "data"){
//                                type = NavType.StringType
//                                defaultValue = ""
//                            }
//                        ),
//                        route = "deep"){entry->
//                        val data = entry.arguments?.getString("data")?:""
//                        Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()) {
//                            Text(text = "This is the Data: $data")
//                        }
//                    }
//                }
               // HomeScreen()
                //ProfileSetting()
                //CallScreenManager()
                //VocConversation()

                //ScreenNav(navHostController = rememberNavController())
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
                //val scope = rememberCoroutineScope()
//                Box (modifier = Modifier
//                    .fillMaxSize()
//                    .background(color = Color(android.graphics.Color.parseColor("#F2EAD5")))){
//                   Button(modifier = Modifier.align(Alignment.Center),onClick = { scope.launch {
//                       try {
//                           val category = string.map {
//                               val res = it.split(",")
//                               Category(color = res[1], icon =res[0] , userEnrolled =(Math.random() * 100).toInt())
//                           }
//                           db.document("top_category").set(CategoryItems(categories = category)).await()
////                           println(GetCourseDatas()?.courses == null)
////                           GetCourseDatas()?.courses?.forEach {
////                               println(it.imageUri)
////                               async {
////                                   db.document("{${it.title}${Timestamp.now()}").set(it).await()
////                               }
////                           }
//                       }catch (e:Exception){
//                           println(e.printStackTrace())
//                           println("Exception in firestore ${e.message}")
//                       }
//                   } }) {
//                       Text(text = "Upload Please", color = Color.White, fontWeight = FontWeight.Bold)
//                   }
//                }
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
