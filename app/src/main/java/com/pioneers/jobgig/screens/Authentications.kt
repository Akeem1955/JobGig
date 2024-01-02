package com.pioneers.jobgig.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.pioneers.jobgig.R
import com.pioneers.jobgig.dataobj.utils.InstructorDesignData
import com.pioneers.jobgig.sealed.CourseContentDesign
import com.pioneers.jobgig.sealed.CourseInfoDesign
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.MapViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


@Composable
fun CourseInfo(
    title: String,
    content:CourseContentDesign,
    infoType:CourseInfoDesign,
    contentType:CourseContentDesign){
    Column {
        Text(text =title,Modifier.padding(start = 12.dp, bottom = 10.dp, end = 8.dp))
        when(contentType){
            CourseContentDesign.Items ->{
                val contentItem = content as CourseContentDesign.ListDesign
                var showState by remember {
                    mutableStateOf(false)
                }
                when(infoType){
                    CourseInfoDesign.BulletDesign ->{
                        Column {
                            if (showState)
                            {
                                contentItem.items.forEach { value ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Circle,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = value)
                                    }
                                }
                            }
                            else if (contentItem.items.size < 4)
                            {
                                contentItem.items.forEach { value ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Circle,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = value)
                                    }
                                }
                            }
                            else
                            {
                                repeat(4) { i ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Circle,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = contentItem.items[i])
                                    }
                                }
                            }
                            TextButton(onClick = { showState = !showState }) {
                                Text(text = if (showState) "Show Less" else "Show More")
                            }
                        }
                    }
                    CourseInfoDesign.CheckDesign -> {
                        Column {
                            if (showState)
                            {
                                contentItem.items.forEach { value ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = value)
                                    }
                                }
                            }
                            else if (contentItem.items.size < 4)
                            {
                                contentItem.items.forEach { value ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = value)
                                    }
                                }
                            }
                            else
                            {
                                repeat(4) { i ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "",
                                            modifier = Modifier.size(8.dp) ,
                                            tint = Color.Gray
                                        )
                                        Text(text = contentItem.items[i])
                                    }
                                }
                            }
                            TextButton(onClick = { showState = !showState }) {
                                Text(text = if (showState) "Show Less" else "Show More")
                            }
                        }
                    }
                    CourseInfoDesign.InstructorDesign -> Unit
                }
            }
            CourseContentDesign.Single ->{
                val contentItem = content as CourseContentDesign.TextDesign
                Text(text = contentItem.item)}
            CourseContentDesign.Instructor ->{
                val contentItem = content as CourseContentDesign.InstructorDesign
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                  contentItem.items.forEach {item->
                      InstructorDesign(data = item.res, name =item.name , description =item.description)
                  }
                }
            }
            is CourseContentDesign.InstructorDesign -> Unit
            is CourseContentDesign.ListDesign -> Unit
            is CourseContentDesign.TextDesign -> Unit
        }
    }
}



@Composable
fun InstructorDesign(data:Any, name: String, description:String){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Spacer(modifier = Modifier.width(6.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data = data).build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .size(64.dp),
            contentDescription = "")
        Spacer(modifier = Modifier.width(6.dp))
        Column(modifier = Modifier.weight(2f,fill = true)) {
            Text(text = name)
            Text(text = description)
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = "")
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(){
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            color = colorResource(id = R.color.btn),
            shape = MaterialTheme.shapes.extraLarge
        )
        {
            Text(text = "Log in", fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(alignment = Alignment.Center)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                OutlinedTextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = "")},
                    supportingText = { Text(text = "")},
                    placeholder = { Text(text = "Email")})
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(value = "",
                    onValueChange = {},
                    trailingIcon = { Icon(painter = painterResource(id = R.drawable.outline_visibility_off_24), contentDescription = ""
                    )},
                    placeholder = { Text(text = "Password")})
                Text(text = "Forgot Password?")
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Login")
                }
                Text(text = "Don't have an account? Sign in")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(){
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            color = colorResource(id = R.color.btn),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(text = "Log in", fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                color = colorResource(id = R.color.white),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(alignment = Alignment.Center)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.fillMaxHeight(0.15f))

                //full name
                OutlinedTextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = ""
                    )},
                    placeholder = { Text(text = "Fullname")})

                //email
                OutlinedTextField(value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = "")},
                    supportingText = { Text(text = "")},
                    placeholder = { Text(text = "Email")})

                //password
                Spacer(modifier = Modifier.height(10.dp))


                OutlinedTextField(value = "",
                    onValueChange = {},
                    trailingIcon = { Icon(painter = painterResource(id = R.drawable.outline_visibility_off_24), contentDescription = ""
                    )},
                    placeholder = { Text(text = "Password")})


                Spacer(modifier = Modifier.fillMaxHeight(0.15f))


                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn)),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth(0.7f),
                    shape = MaterialTheme.shapes.small) {
                    Text(text = "Login With Google", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun HomeCardView(){
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(200.dp)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.cow),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                        contentDescription ="" )
                    Spacer(modifier = Modifier
                        .weight(3f)
                        .height(90.dp))
                    Icon(tint= MaterialTheme.colorScheme.surfaceTint,imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription ="" , modifier = Modifier
                        .padding(end = 8.dp)
                        .size(34.dp, 24.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(modifier = Modifier.padding(start = 16.dp),text = "Searching For Jobs ?", fontSize = MaterialTheme.typography.titleLarge .fontSize, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(modifier = Modifier.padding(start = 16.dp),text = "Submit Your Info and get a gig")
            }
        }
    }
}

@Composable
fun LazyGrids(item:String){
    Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceVariant) {
        Text(text = item, modifier = Modifier
            .padding(16.dp)
            .wrapContentSize())
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowS(){
    var scrollState = rememberScrollState()
    var items = listOf<String>("Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer","Catering Services is here","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer")
    FlowRow(verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            .verticalScroll(state = scrollState)) {
        items.forEach { item->
            LazyGrids(item = item)
        }
    }
}

@Composable
fun EmployeeDetails(){

}
@Composable
fun AboutMe(aboutMe:String, title:String){
    Column {
        Text(text = title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 24.dp)) {
            Text(text = aboutMe, modifier = Modifier.padding(start = 8.dp, bottom = 24.dp, end = 16.dp, top = 8.dp))
        }
    }

}


@Composable
fun VideoAboutMyWork(uri: Uri?){
    val ctx = LocalContext.current
    val owner = LocalLifecycleOwner.current
    var videoDuration by rememberSaveable {
        mutableLongStateOf(0)
    }
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val player by remember {
        mutableStateOf(ExoPlayer.Builder(ctx).build())
    }
    DisposableEffect(key1 = owner){
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
            when(event){
                Lifecycle.Event.ON_STOP -> {
                    println("This is on stop")
                    videoDuration=player.contentPosition
                    player.stop()
                }
                Lifecycle.Event.ON_RESUME -> {
                    println("This is on Resume")

                }
                Lifecycle.Event.ON_PAUSE -> {
                    println("This is on Pause")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    player.release()
                    println("This is on Destroy")

                }
                else -> Unit
            }

        }
        owner.lifecycle.addObserver(observer)
        onDispose { owner.lifecycle.removeObserver(observer) }
    }
    LaunchedEffect(key1 = true){
        if (uri != null){
            player.setMediaItem(MediaItem.fromUri(uri))
            player.seekTo(videoDuration)
            player.prepare()
        }
    }





    Surface(shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)) {
        VideoPlayer(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), player = player, lifecycle = lifecycle)
    }
}
@Composable
fun VideoPlayer(modifier: Modifier, player:ExoPlayer, lifecycle: Lifecycle.Event){
    AndroidView(factory = { ctx ->PlayerView(ctx).also { it.player = player }},
        modifier = modifier,
        update = {
            when(lifecycle){
                Lifecycle.Event.ON_RESUME ->{it.onResume()}
                Lifecycle.Event.ON_PAUSE ->{it.onPause();it.player?.pause()}
                else -> Unit
            }
        }
    )
}
@Composable
fun TopCategoryItem(category:String){
    Surface(shape = MaterialTheme.shapes.medium, modifier = Modifier.width(100.dp), color = MaterialTheme.colorScheme.primary) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)) {
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(R.drawable.hen).build(),
                    contentDescription ="",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 64.dp, height = 64.dp)
                        .clip(CircleShape))
            }
            Text(text = category, modifier = Modifier.padding(bottom = 8.dp), textAlign = TextAlign.Center)
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DisplayRationale(permisionState:MultiplePermissionsState, rationale:List<String>){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 350.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                var count = 0
                val packageName =LocalContext.current.packageName
                val context = LocalContext.current
                permisionState.permissions.forEach { perm->
                    if (perm.status.shouldShowRationale){
                        Text(text = rationale[count])
                    }else if(!perm.status.isGranted && !perm.status.shouldShowRationale){
                        Text(text = "Access to ${perm.permission} as been permanently revoked by You kindly enable it in the setting to be able to use the feature.")
                    }
                    count++
                }
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.`package`= packageName
                    context.startActivity(intent)
                }) {
                    Text(text = "Grant Access")
                }
            }
        }
    }
}

@Composable
fun PopularCourseCard(){
    Column(modifier = Modifier.width(200.dp)) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant ,shape = MaterialTheme.shapes.small, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {

        }
    }
}

@Composable
fun EnrollCourse(top:Dp, bottom:Dp){
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(R.drawable.hen).build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.TopCenter)
                .fillMaxHeight(0.5f))
        Surface(color = MaterialTheme.colorScheme.surfaceVariant,modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)) {

        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.statusBarsPadding()) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
        }
    }
}



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DisplayRationale(permisionState:PermissionState, rationale: String){
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 200.dp))  {
            Column(modifier = Modifier.fillMaxSize()){
                if(permisionState.status.shouldShowRationale){
                    Text(text = rationale)
                }else if (permisionState.status.shouldShowRationale&&permisionState.status.isGranted){
                    Text(text = "Permission Denied Permanently By You, Kindly Grant Access In the Setting")
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapView(locationServices:FusedLocationProviderClient, viewModel:MapViewModel, polyline:List<LatLng>?){
    Box(modifier = Modifier.fillMaxSize()) {
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(mapToolbarEnabled = true, compassEnabled = true, myLocationButtonEnabled = true, zoomControlsEnabled = true)) }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true, isBuildingEnabled = true))
        }
        val permissions = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION) )
        val owner = LocalLifecycleOwner.current
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.locState, 18f)
        }
        DisposableEffect(key1 = owner){
            val observer = LifecycleEventObserver{ _, event->
                if(event ==  Lifecycle.Event.ON_RESUME && !permissions.allPermissionsGranted){
                    permissions.launchMultiplePermissionRequest()
                }
            }
            owner.lifecycle.addObserver(observer)
            onDispose {owner.lifecycle.removeObserver(observer)}
        }
        LaunchedEffect(key1 = true){
            try {
                println("Worked As expected")
                val realLocation = locationServices.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).await()
                println("Worked As expected....")
                viewModel.updateLatLng(realLocation.latitude, realLocation.longitude)
                cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.locState),1000)
            }catch (e:Exception){
                println("Error happened ${e.printStackTrace()}")
            }
            while (true){
                println("About to Sleep........")
                delay(5000)
                try {
                    if (permissions.allPermissionsGranted){
                        println("Worked As expected")
                        val currentLocation = locationServices.lastLocation.await()
                        println("Worked As expected....")
                        viewModel.updateLatLng(currentLocation.latitude, currentLocation.longitude)
                        cameraPositionState.animate(CameraUpdateFactory.newLatLng(viewModel.locState),1000)
                        println("Worked As expected.... finish")
                    }
                    else{
                        println("all permission not granted")
                    }
                }catch (e:Exception){
                    println("Error happened ${e.printStackTrace()}")
                }
            }
        }

        if(permissions.allPermissionsGranted){
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState){
                if (polyline != null){
                    Polyline(points = polyline, color = Color.Magenta, width = 2f)
                    Marker(icon = BitmapDescriptorFactory.fromResource(R.drawable.noto_hammer_and_wrench), state = MarkerState(position = polyline[0]))
                    Marker(icon = BitmapDescriptorFactory.fromResource(R.drawable.mingcute_location_2_fill), state = MarkerState(position = polyline[polyline.lastIndex]))

                }
                Circle(center =viewModel.locState , radius = 50.0, fillColor = Color.Magenta, strokeColor = Color.Magenta, strokeWidth = 1f)
            }
        }
        else{
            DisplayRationale(permisionState = permissions, rationale = listOf(stringResource(id = R.string.ACCESS_FINE_LOCATION),
                stringResource(id = R.string.ACCESS_COARSE_LOCATION)))
        }

    }
}


@Composable
fun SelectWorker(){
    var isLoading = rememberSaveable {
        mutableStateOf(true)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center,modifier = Modifier.fillMaxSize()) {
       if (isLoading.value){
           CircularProgressIndicator()
       }else{

       }

    }
}
@Composable
fun RatingBar(ratings:Double){
    var rating = ratings
    Row {
        repeat(5){
            println(rating)
            if (rating >= 1){
                Icon(imageVector = Icons.Rounded.StarRate, contentDescription ="", tint = Color.Green )
            }else if (rating > 0){
                Icon(imageVector = Icons.Rounded.StarHalf, contentDescription ="", tint = Color.Green )
            }
            else{
                Icon(imageVector = Icons.Rounded.StarRate, contentDescription ="", tint = Color.Gray )
            }
            rating--
        }
    }
}


@Composable
fun WorkerCards(modifier: Modifier = Modifier, painter: Painter, name:String, distance:String,ratings: Double,duration:String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(start = 16.dp, end = 8.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(text = name, fontSize = MaterialTheme.typography.titleSmall   .fontSize)
                    Text(text = distance, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    RatingBar(ratings = ratings)
                    Text(text = duration,fontSize = MaterialTheme.typography.bodySmall.fontSize)
                }
            }
        }
    }
}

@Composable
fun NextedLay() {
    var test = listOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25)
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .background(color = Color.Magenta)
        ) {
            LazyColumn(modifier = Modifier
                .background(color = Color.Magenta)
                .fillMaxWidth()) {
                items(test) { t ->
                    Text(text = t.toString())
                }
            }
        }
        LazyColumn(modifier = Modifier
            .background(color = Color.Green)
            .fillMaxWidth()) {
            items(test) { t ->
                Text(text = t.toString())
            }
        }
    }
}







@Composable
fun ProfilePic(uri: Uri? = Uri.parse("https://images.unsplash.com/photo-1702924507770-a8a2ecb410b2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D")){}


@Composable
fun MyPastWorkGallery(pastWork:List<String>){
    var state = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp,
        contentPadding = PaddingValues(8.dp) ,
        modifier = Modifier.fillMaxSize(),
        state = state){
        items(pastWork){pastwork -> Surface(shape=MaterialTheme.shapes.medium) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .crossfade(1000)
                    .fallback(R.drawable.round_image_24)
                    .placeholder(R.drawable.round_image_24)
                    .data(pastwork)
                    .build(),
                placeholder = painterResource(id = R.drawable.round_image_24) ,
                modifier = Modifier.heightIn(min = 100.dp) ,
                contentDescription ="Image Of Past Work" )

        }}
    }
}
//




















@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    JobGigTheme {
        var uri = Uri.parse("android.resource://" + LocalContext.current.packageName + "/" + R.raw.alarm_manager)
       var locationServices = LocationServices.getFusedLocationProviderClient(LocalContext.current)
        var viewModel:MapViewModel= viewModel()
//        val permissions = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION) )
//        val owner = LocalLifecycleOwner.current
//        DisposableEffect(key1 = owner){
//            val observer = LifecycleEventObserver{ _, event->
//                if(event ==  Lifecycle.Event.ON_RESUME ){
//                    permissions.launchMultiplePermissionRequest()
//                }
//            }
//            owner.lifecycle.addObserver(observer)
//            onDispose {owner.lifecycle.removeObserver(observer)}
//        }
        var aboutme ="Dedicated  "
        val instruct = listOf<InstructorDesignData>(InstructorDesignData(name = "Adetunji Akeem", description = "A Experience Carpenter With 20 years of experience", res = R.drawable.hen, uri = null),InstructorDesignData(name = "Adetunji Azeem Iku", description = "A Experience Carpenter With 20 years of experience", res = R.drawable.cow, uri = null))
        var item = listOf<String>("How To Operate The Camera", "Basics use of CameraX", "How To Find The Perfect Scene", "Finding The right Angle", "Summary And Bonus")
        var items= listOf<String>(
            "https://images.unsplash.com/photo-1695653420644-ab3d6a039d53?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHx8",
            "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
            "https://images.unsplash.com/photo-1697665666330-7acf230fa830?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHx8",
            "https://images.unsplash.com/photo-1702924507770-a8a2ecb410b2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE2fDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
            "https://plus.unsplash.com/premium_photo-1700984292461-fa2d83c28c6b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDEwfDZzTVZqVExTa2VRfHxlbnwwfHx8fHw%3D",
            "https://images.unsplash.com/photo-1697450229849-30db7a91427e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDN8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D",
            "https://images.unsplash.com/photo-1703235379678-1e14654d69aa?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDd8NnNNVmpUTFNrZVF8fGVufDB8fHx8fA%3D%3D"
        )
       Box (modifier = Modifier.fillMaxSize()){
          CourseInfo(title = "Instructor", content = CourseContentDesign.InstructorDesign(instruct), infoType = CourseInfoDesign.InstructorDesign, contentType =CourseContentDesign.Instructor )
          // Login()
           //InstructorDesign()
       //HomeCardView()
       //SelectWorker()
           //NextedLay()
           //WorkerCards(painter = painterResource(id = R.drawable.hen), name ="Cockerel John" , distance ="3km", ratings = 4.0, duration ="10 min" )
           //RatingBar(ratings = 3.5)
           //MapView(locationServices, viewModel = viewModel,null)
           //TopCategoryItem(category = "Plumbing Repair")
           //VideoAboutMyWork(uri = uri)
//          Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)  ) {
//              PopularCourseCard()
//              PopularCourseCard()
//          }
           //EnrollCourse()
          //AboutMe(aboutMe = aboutme, title = "Name")
       }
        //MyPastWorkGallery(pastWork = items)
       //FlowS()
//        var items = listOf<String>("Catering Services","Plumber","Elephant","Engineering","Electrician","Headdress", "Fashion Designer")
//        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3) ){
//           items(items){item-> LazyGrids(item = item)}
//        }
    }
}