package com.pioneers.jobgig.screens


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
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
import com.pioneers.jobgig.dataobj.utils.CourseContent
import com.pioneers.jobgig.sealed.CourseContentDesign
import com.pioneers.jobgig.sealed.CourseInfoDesign
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.CourseViewModel
import com.pioneers.jobgig.viewmodels.MapViewModel
import com.pioneers.jobgig.viewmodels.VideoPlayViewModel
import com.pioneers.jobgig.viewmodels.VocConnectViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await


@Composable
fun CourseInfo(
    title: String,
    content:CourseContentDesign,
    infoType:CourseInfoDesign,
    navController: NavController,
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
                    CourseInfoDesign.InstructorDesign -> {
                        Unit
                    }
                }
            }
            CourseContentDesign.Single ->{
                var showState by remember {
                    mutableStateOf(false)
                }
                val contentItem = content as CourseContentDesign.TextDesign
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = contentItem.item,modifier = if (showState) Modifier.wrapContentHeight() else Modifier.height(50.dp) )
                    TextButton(onClick = { showState = !showState }) {
                        Text(text = if (showState) "Show Less" else "Show More")
                    }
                }}
            CourseContentDesign.Instructor ->{
                val contentItem = content as CourseContentDesign.InstructorDesign
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                  contentItem.items.forEach {item->
                      InstructorDesign(uri = item.uri, name =item.name , description =item.description, navController =navController )
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
fun InstructorDesign(uri:String, name: String, description:String,navController: NavController){
    val descrip = " $description"
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Spacer(modifier = Modifier.width(6.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data = uri).error(R.drawable.round_image_24).build(),
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
        println(uri +"  original")
        IconButton(onClick = {navController.navigate(route = ScreenRoute.InstructorDetailLScreenRoute.addUri(uri.replace("/",","),description))}) {
            Icon(imageVector = Icons.Rounded.ArrowForwardIos, contentDescription = "")
        }
    }

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
fun VideoAboutMyWork(uri: Uri?, title: String){
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
            player.setMediaItems(emptyList())
            player.setMediaItem(MediaItem.fromUri(uri))
            player.seekTo(videoDuration)
            player.prepare()
        }
    }

    Column {
        Text(text = title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(shape = MaterialTheme.shapes.medium,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 32.dp)
                .height(150.dp)) {
            VideoPlayer(modifier = Modifier
                .fillMaxSize(), player = player, lifecycle = lifecycle)
        }
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
fun TopCategoryItems(category:String){
    Surface(shape = MaterialTheme.shapes.medium, modifier = Modifier.width(100.dp), color = MaterialTheme.colorScheme.primary) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)) {
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(R.drawable.round_account_circle_24).build(),
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
    LaunchedEffect(key1 = true){
        permisionState.launchMultiplePermissionRequest()
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier
            .padding(horizontal = 16.dp)
            .heightIn(min = 300.dp)) {
            Column {
                var count = 0
                val packageName =LocalContext.current.packageName
                val context = LocalContext.current
                permisionState.permissions.forEach { perm->
                    if (perm.status.shouldShowRationale){
                        Text(text = rationale[count])
                    }
                    else if(!perm.status.isGranted && !perm.status.shouldShowRationale){
                        Text(text = "Access to ${perm.permission} as been permanently revoked by You kindly enable it in the setting to be able to use the feature.")
                    }
                    count++
                }
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.data = Uri.fromParts("package",packageName,null)
                    context.startActivity(intent)
                }) {
                    Text(text = "Grant Access")
                }
            }
        }
    }
}





@Composable
fun Duration(duration: String){
    Row(verticalAlignment = Alignment.CenterVertically ,horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Icon(imageVector = Icons.Rounded.AccessTime, contentDescription = "", tint = Color.Magenta)
        Text(text = duration, fontSize = MaterialTheme.typography.labelSmall.fontSize, color = MaterialTheme.colorScheme.surfaceTint)
    }
}





@Composable
fun  CourseIntroRate(title: String, duration: String, ratings: Double, studentEnrolled:Int, numRating:Int){
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f, fill = true), fontSize = MaterialTheme.typography.titleLarge.fontSize)
            Duration(duration = duration)
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)){
            Text(text = ratings.toString(), fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold)
            Icon(imageVector = Icons.Rounded.StarRate, contentDescription ="", tint = Color.Green, modifier = Modifier.size(16.dp))

        }
        Row{
            Text(text = "($numRating ratings) ${String.format("%,d",studentEnrolled)} learners", fontSize = MaterialTheme.typography.labelSmall.fontSize)
        }
    }
}











@Composable
fun CourseVideoItem(content:CourseContent, pos:Int, viewModel: CourseViewModel){
    Row(modifier = Modifier.clickable {viewModel.playVideo(pos) } ,verticalAlignment = Alignment.CenterVertically ,horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "$pos")
        Column(verticalArrangement = Arrangement.spacedBy(4.dp),modifier = Modifier.weight(1f)) {
            Text(overflow = TextOverflow.Ellipsis ,maxLines = 1 ,text = content.title)
            Text(overflow = TextOverflow.Ellipsis ,maxLines = 1 ,text = content.duration, fontSize = MaterialTheme.typography.labelSmall.fontSize)
        }
    }
}





@Composable
fun CourseVideoItems(viewModel: CourseViewModel, contents:List<CourseContent>){
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),contentPadding = PaddingValues(16.dp)){
       itemsIndexed(contents){index,item->
           CourseVideoItem(content = item, pos = index, viewModel = viewModel)
       }
    }
}





@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CourseVideoPlayScreen(viewModel:VideoPlayViewModel?){
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val tabItem = listOf("Lectures", "More")
    val pagerState = rememberPagerState(initialPage = 0) {
        tabItem.size
    }
    LaunchedEffect(key1 = selectedTabIndex){
        pagerState.animateScrollToPage(page = selectedTabIndex)
    }
    LaunchedEffect(key1 = pagerState.currentPage,pagerState.isScrollInProgress){
        if (!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }


    Column {
       Box(modifier = Modifier
           .fillMaxWidth()
           .background(color = Color.Black)
           .height(250.dp)) {
           //VideoPlayer(modifier = Modifier.fillMaxSize(), player = , lifecycle = )
       }
       Column(modifier = Modifier.weight(1f)) {
           TabRow(selectedTabIndex = selectedTabIndex) {
               tabItem.forEachIndexed { index, s ->
                   Tab(selected = index == selectedTabIndex,
                       onClick = { selectedTabIndex = index }, 
                       text ={ Text(text = s)} 
                   )
               }
           }
           HorizontalPager(state = pagerState, modifier = Modifier.weight(1f,true)) {index ->
               Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                   Text(text = tabItem[index])
               }
           }
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
fun MapView(modifier: Modifier,locationServices:FusedLocationProviderClient?, viewModel:VocConnectViewModel?, polyline:List<LatLng>?, workers:List<LatLng>?){
    Box(modifier = modifier) {
        val uiSettings by remember {
            mutableStateOf(MapUiSettings(mapToolbarEnabled = true, compassEnabled = true, myLocationButtonEnabled = true, zoomControlsEnabled = true)) }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true, isBuildingEnabled = true))
        }
        val permissions = rememberMultiplePermissionsState(permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION) )
        val owner = LocalLifecycleOwner.current
        val cameraPositionState = rememberCameraPositionState {
            if (viewModel != null) {
                position = CameraPosition.fromLatLngZoom(viewModel.locState, 18f)
            }
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
                if (viewModel == null)return@LaunchedEffect
                if (locationServices == null)return@LaunchedEffect
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
                    if (permissions.allPermissionsGranted && viewModel !=null && locationServices != null){
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
                if (workers != null){
                    val sam = workers.map {
                        Marker(icon = BitmapDescriptorFactory.fromResource(R.drawable.noto_hammer_and_wrench), state = MarkerState(position = it))
                    }
                }
                //Circle(center =viewModel.locState , radius = 50.0, fillColor = Color.Magenta, strokeColor = Color.Magenta, strokeWidth = 1f)
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
fun WorkerCards(onClick:()->Unit, modifier: Modifier = Modifier, profilePic: String, name:String, distance:String,ratings: Double,duration:String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .clickable {  }
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(modifier = Modifier.size(80.dp) ,contentScale = ContentScale.FillBounds,model = ImageRequest.Builder(LocalContext.current).data(profilePic).error(R.drawable.round_account_circle_24).placeholder(R.drawable.restore).build(), contentDescription = "")
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
fun MyPastWorkGallery(pastWork:List<String>,modifier: Modifier){
    val state = rememberLazyStaggeredGridState()
    Column {
        Text(text = "Prev Work", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 24.dp))
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(125.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(8.dp) ,
            modifier = modifier,
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
}
//




@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true)
@Composable
fun LoginPreview(){
    JobGigTheme {
        WorkerCards(profilePic = "", name = "Adewale Samuel", distance ="5km" , ratings =3.5 , duration ="30min", onClick = {} )

    }
}