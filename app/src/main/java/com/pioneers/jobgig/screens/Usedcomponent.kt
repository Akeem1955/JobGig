package com.pioneers.jobgig.screens

//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.layout.wrapContentWidth
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.AccountCircle
//import androidx.compose.material.icons.outlined.ArrowForwardIos
//import androidx.compose.material.icons.outlined.Email
//import androidx.compose.material.icons.outlined.Person
//import androidx.compose.material.icons.rounded.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.pioneers.jobgig.R
//import com.pioneers.jobgig.dataobj.utils.InstructorDesignData
//import com.pioneers.jobgig.sealed.CourseContentDesign
//import com.pioneers.jobgig.sealed.CourseInfoDesign
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Logins(){
//    Box(modifier = Modifier.fillMaxSize()) {
//        Surface(modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp),
//            color = colorResource(id = R.color.btn),
//            shape = MaterialTheme.shapes.extraLarge
//        )
//        {
//            Text(text = "Log in", fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
//                color = colorResource(id = R.color.white),
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .wrapContentHeight()
//                    .align(alignment = Alignment.Center)
//            )
//        }
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 80.dp),
//            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
//                OutlinedTextField(value = "",
//                    onValueChange = {},
//                    leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "") },
//                    supportingText = { Text(text = "") },
//                    placeholder = { Text(text = "Email") })
//                Spacer(modifier = Modifier.height(10.dp))
//                OutlinedTextField(value = "",
//                    onValueChange = {},
//                    trailingIcon = { Icon(painter = painterResource(id = R.drawable.outline_visibility_off_24), contentDescription = ""
//                    )
//                    },
//                    placeholder = { Text(text = "Password") })
//                Text(text = "Forgot Password?")
//                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
//                Button(onClick = { /*TODO*/ },
//                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
//                    modifier = Modifier.fillMaxWidth(0.7f),
//                    shape = MaterialTheme.shapes.small) {
//                    Text(text = "Login")
//                }
//                Text(text = "Don't have an account? Sign in")
//            }
//        }
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SignUp(){
//    Box(modifier = Modifier.fillMaxSize()) {
//        Surface(modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp),
//            color = colorResource(id = R.color.btn),
//            shape = MaterialTheme.shapes.extraLarge
//        ) {
//            Text(text = "Log in", fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
//                color = colorResource(id = R.color.white),
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .wrapContentHeight()
//                    .align(alignment = Alignment.Center)
//            )
//        }
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(top = 80.dp),
//            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
//
//                //full name
//                OutlinedTextField(value = "",
//                    onValueChange = {},
//                    leadingIcon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = ""
//                    )
//                    },
//                    placeholder = { Text(text = "Fullname") })
//
//                //email
//                OutlinedTextField(value = "",
//                    onValueChange = {},
//                    leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "") },
//                    supportingText = { Text(text = "") },
//                    placeholder = { Text(text = "Email") })
//
//                //password
//                Spacer(modifier = Modifier.height(10.dp))
//
//
//                OutlinedTextField(value = "",
//                    onValueChange = {},
//                    trailingIcon = { Icon(painter = painterResource(id = R.drawable.outline_visibility_off_24), contentDescription = ""
//                    )
//                    },
//                    placeholder = { Text(text = "Password") })
//
//
//                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
//
//
//                Button(onClick = { /*TODO*/ },
//                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
//                    modifier = Modifier.fillMaxWidth(0.7f),
//                    shape = MaterialTheme.shapes.small) {
//                    Text(text = "Login")
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Button(onClick = { /*TODO*/ },
//                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
//                    modifier = Modifier.fillMaxWidth(0.7f),
//                    shape = MaterialTheme.shapes.small) {
//                    Text(text = "Login With Google", color = Color.Gray)
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun EnrollCourse(top: Dp, bottom: Dp, title: String, duration: String, ratings: Double, studentEnrolled: Int, numRating: Int){
//    Box(modifier = Modifier.fillMaxSize()) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current).data(R.drawable.camera).build(),
//            contentDescription = "",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(alignment = Alignment.TopCenter)
//                .fillMaxHeight(0.4f))
//        Surface(
//            color = MaterialTheme.colorScheme.surfaceVariant,
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.7f)
//                .align(Alignment.BottomCenter),
//            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)) {
//            Column(modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = bottom)) {
//                LazyColumn(state = rememberLazyListState(), modifier= Modifier
//                    .padding(start = 16.dp, end = 16.dp, bottom = bottom)
//                    .weight(1f, true)){
//                    item {
//                        CourseIntroRate(
//                            title = title,
//                            duration = duration,
//                            ratings = ratings,
//                            studentEnrolled = studentEnrolled ,
//                            numRating = numRating
//                        )
//                    }
//                    item {
//                        val item = listOf("How To Operate The Camera", "Basics use of CameraX", "How To Find The Perfect Scene", "Finding The right Angle", "Summary And Bonus")
//                        CourseInfo(title = "What You will Learn", content = CourseContentDesign.ListDesign(item), infoType = CourseInfoDesign.CheckDesign, contentType = CourseContentDesign.Items )
//                    }
//                    item {
//                        val items = listOf<String>("A Camera", "Dedicated MindSet", "Must Radicalized", "Taught In Yoruba Language", "Passion")
//                        CourseInfo(title = "Requirements", content = CourseContentDesign.ListDesign(items), infoType = CourseInfoDesign.BulletDesign, contentType = CourseContentDesign.Items )
//                    }
//                    item {
//                        val item = stringResource(id = R.string.get_startedspeech)
//                        CourseInfo(title = "Description", content = CourseContentDesign.TextDesign(item), infoType = CourseInfoDesign.CheckDesign, contentType = CourseContentDesign.Single)
//
//                    }
//                    item {
//                        val instruct = listOf<InstructorDesignData>(InstructorDesignData(name = "Adetunji Akeem", description = "An Experience Photographer With 20 years of experience", res = R.drawable.noto_hammer_and_wrench, uri = null),
//                            InstructorDesignData(name = "Adetunji Azeem Iku", description = "A Experience Image Editor With 5 years of experience", res = R.drawable.automotive, uri = null)
//                        )
//                        CourseInfo(title = "Instructor", content = CourseContentDesign.InstructorDesign(instruct), infoType = CourseInfoDesign.InstructorDesign, contentType = CourseContentDesign.Instructor )
//                    }
//                }
//                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background) {
//                    Row(modifier = Modifier.padding(4.dp),verticalAlignment = Alignment.CenterVertically) {
//                        Text(text = "Free", modifier = Modifier
//                            .weight(1f, true)
//                            .background(color = MaterialTheme.colorScheme.background), textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.ExtraBold)
//                        Text(color = Color.White,text = "Enroll Now", modifier = Modifier
//                            .background(color = Color.Magenta)
//                            .weight(2f, true)
//                            .padding(12.dp), textAlign = TextAlign.Center)
//                    }
//                }
//            }
//        }
//        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.statusBarsPadding()) {
//            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
//        }
//    }
//}
//@Composable
//fun LazyGrids(item:String){
//    Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceVariant) {
//        Text(text = item, modifier = Modifier
//            .padding(16.dp)
//            .wrapContentSize())
//    }
//}
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun FlowS(){
//    var scrollState = rememberScrollState()
//    var items = listOf<String>("Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer")
//    FlowRow(verticalArrangement = Arrangement.spacedBy(8.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
//            .verticalScroll(state = scrollState)) {
//        items.forEach { item->
//            LazyGrids(item = item)
//        }
//    }
//}
//
//@Composable
//fun EmployeeDetails(){
//
//}
//
//
//@Composable
//fun HomeCardViewT(){
//    Box(modifier = Modifier.fillMaxSize()) {
//        Surface(
//            modifier = Modifier
//                .fillMaxWidth(0.9f)
//                .height(200.dp)
//                .padding(horizontal = 8.dp, vertical = 16.dp),
//            color = MaterialTheme.colorScheme.surfaceVariant,
//            shape = MaterialTheme.shapes.medium) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Image(
//                        painter = painterResource(id = R.drawable.map_plumber),
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .size(64.dp)
//                            .clip(CircleShape),
//                        contentDescription ="" )
//                    Spacer(modifier = Modifier
//                        .weight(1f)
//                        .height(80.dp))
//                    Icon(tint= MaterialTheme.colorScheme.surfaceTint,imageVector = Icons.Outlined.ArrowForwardIos, contentDescription ="" , modifier = Modifier
//                        .padding(end = 8.dp)
//                        .size(34.dp, 24.dp))
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(modifier = Modifier.padding(start = 16.dp),text = "Searching For Jobs ?", fontSize = MaterialTheme.typography.titleLarge .fontSize, fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.height(10.dp))
//                Text(modifier = Modifier.padding(start = 16.dp),text = "Submit Your Info and get a gig")
//            }
//        }
//    }
//}
//
//@Composable
//fun PopularCourseCards(){
//    Column(modifier = Modifier.width(200.dp)) {
//        Surface(color = MaterialTheme.colorScheme.surfaceVariant ,shape = MaterialTheme.shapes.small, modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)) {
//
//        }
//    }
//}

//
//
//val backPressed = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
//DisposableEffect(key1 =backPressed ){
//    val observer =object : OnBackPressedCallback(true){
//        override fun handleOnBackPressed() {
//            TODO("Not yet implemented")
//        }
//    }
//    backPressed?.addCallback(observer)
//    onDispose {
//        observer.remove()
//    }
//}
//var uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.raw.alarm_manager)
//var locationServices = LocationServices.getFusedLocationProviderClient(LocalContext.current)
//var viewModel: MapViewModel = viewModel()
////        val permissions = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION) )
////        val owner = LocalLifecycleOwner.current
////        DisposableEffect(key1 = owner){
////            val observer = LifecycleEventObserver{ _, event->
////                if(event ==  Lifecycle.Event.ON_RESUME ){
////                    permissions.launchMultiplePermissionRequest()
////                }
////            }
////            owner.lifecycle.addObserver(observer)
////            onDispose {owner.lifecycle.removeObserver(observer)}
////        }
//var aboutme ="Dedicated  "
//
//val instruct = listOf<InstructorDesignData>(InstructorDesignData(name = "Adetunji Akeem", description = "A Experience Carpenter With 20 years of experience", res = R.drawable.map_plumber, uri = null),
//    InstructorDesignData(name = "Adetunji Azeem Iku", description = "A Experience Carpenter With 20 years of experience", res = R.drawable.noto_hammer_and_wrench, uri = null)
//)
//var item = listOf<String>("How To Operate The Camera", "Basics use of CameraX", "How To Find The Perfect Scene", "Finding The right Angle", "Summary And Bonus")
//var items= listOf<String>(
//    "https://images.unsplash.com/photo-1695653420644-ab3d6a039d53?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHx8",
//    "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
//    "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
//    "https://images.unsplash.com/photo-1702924507770-a8a2ecb410b2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
//    "https://plus.unsplash.com/premium_photo-1700984292461-fa2d83c28c6b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDEwfDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
//    "https://images.unsplash.com/photo-1697450229849-30db7a91427e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDN8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D",
//    "https://images.unsplash.com/photo-1703235379678-1e14654d69aa?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDd8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D"
//)
//Box (modifier = Modifier.fillMaxSize()){
//    //CourseInfo(title = "Instructor", content = CourseContentDesign.InstructorDesign(instruct), infoType = CourseInfoDesign.InstructorDesign, contentType =CourseContentDesign.Instructor )
//    // Login()
//    //HomeCardViewT()
//    //CourseVideoItems()
//    //CourseVideoPlayScreen(viewModel = null)
//    //InstructorDesign()
//    //HomeCardView()
//    //SelectWorker()
//    //NextedLay()
//    //WorkerCards(painter = painterResource(id = R.drawable.hen), name ="Cockerel John" , distance ="3km", ratings = 4.0, duration ="10 min" )
//    //RatingBar(ratings = 3.5)
//    //MapView(locationServices, viewModel = viewModel,null)
//    //TopCategoryItem(category = "Plumbing Repair",)
//    //VideoAboutMyWork(uri = uri)
////          Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)  ) {
//    //PopularCourseCard()
////              PopularCourseCard()
////          }
//    //EnrolledScreen()
//    //EnrollCourse(top = 1.dp, bottom = 16.dp, title = "Learn The Basics About Canon Eos", duration = "12h 45min", ratings = 4.3, studentEnrolled =3598 , numRating =400 )
//    //CourseIntroRate(title = "Learn The Basics About Canon Eos", duration = "12h 45min", ratings = 4.3, studentEnrolled =3598 , numRating =400 )
////           Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
////               Spacer(modifier = Modifier.width(16.dp))
////               Text(text = "Learn The Basics About Canon Eos", modifier = Modifier.weight(1f, fill = true), fontSize = MaterialTheme.typography.titleLarge.fontSize)
////               Duration(duration = "12h 30mins")
////           }
//    //AboutMe(aboutMe = aboutme, title = "Name")
//}
////MyPastWorkGallery(pastWork = items)
////FlowS()
////        var items = listOf<String>("Catering Services","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer")
////        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3) ){
////           items(items){item-> LazyGrids(item = item)}
////        }


//@Composable
//fun EnrolledScreen(){
//    Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
//        Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier.padding(12.dp)) {
//            IconButton(onClick = { }) {
//                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
//            }
//            Row(modifier = Modifier.heightIn(max = 120.dp),horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                AsyncImage(modifier = Modifier.weight(1f,true),model = ImageRequest.Builder(LocalContext.current).data(R.drawable.camera).build(), contentDescription = "")
//
//                Column(modifier = Modifier.weight(2f,true) ,verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                    Text(text = "You are now enrolled in:")
//                    Text(text = "Python For Intermediate Learners")
//                    Text(text = "Peter, james")
//                }
//            }
//
//            Button(colors = ButtonDefaults.buttonColors(contentColor = Color.White,containerColor = colorResource(id = R.color.btn)) ,shape = MaterialTheme.shapes.small,modifier = Modifier.fillMaxWidth(),onClick = { /*TODO*/ }) {
//                Text(text = "Get Started")
//            }
//        }
//    }
//}


val myVideoUri = "https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseVideo%2Fpaint.mp4?alt=media&token=6d72f250-b7e1-4ec6-a27b-0a903307f7ad"


var items= listOf<String>(
    "https://images.unsplash.com/photo-1695653420644-ab3d6a039d53?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHx8",
    "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
    "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
    "https://images.unsplash.com/photo-1702924507770-a8a2ecb410b2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
    "https://plus.unsplash.com/premium_photo-1700984292461-fa2d83c28c6b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDEwfDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
    "https://images.unsplash.com/photo-1697450229849-30db7a91427e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDN8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D",
    "https://images.unsplash.com/photo-1703235379678-1e14654d69aa?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDd8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D"
)