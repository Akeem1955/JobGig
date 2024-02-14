package com.pioneers.jobgig.screens

import android.Manifest
import android.content.Context
import android.content.res.Configuration
import android.location.LocationManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.PhoneInTalk
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.pioneers.jobgig.ChatRoom
import com.pioneers.jobgig.R
import com.pioneers.jobgig.UserMessage
import com.pioneers.jobgig.viewmodels.VocConnectViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ServiceSearch(viewmodel:VocConnectViewModel, navController: NavController){
    var state by rememberSaveable {
        mutableStateOf(false)
    }
    if (state){
        Dialog(onDismissRequest = { state = !state }) {
            Surface(shape = MaterialTheme.shapes.large) {
                Column {
                    Text(text = "Sorry no Vocational worker of that category", modifier = Modifier.padding(32.dp))
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { state = !state }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
    val context =LocalContext.current
    val locationManager = remember { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    val isLocationEnabled = remember { locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) }
    val permissions = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION) )
    if (permissions.allPermissionsGranted && isLocationEnabled){
        Surface(color = MaterialTheme.colorScheme.background){
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                SearchCourseBtn(query = viewmodel.query,
                    onQuery = {update->viewmodel.updateQuery(update)},
                    modifier = Modifier.padding( vertical = 16.dp),
                    onSearch ={
                        state = viewmodel.filteredVoc.isEmpty()
                        onSearch(viewmodel, navController)
                    }, items = viewmodel.filteredVoc)
                Text(text = "Categories", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                Flows(items = viewmodel.vocCategory,
                    Modifier
                        .weight(1f, true)
                        .padding(horizontal = 12.dp), onSearch = {update->viewmodel.updateQuery(update) ;state = viewmodel.filteredVoc.isEmpty()
                        onSearch(viewmodel, navController)})
            }
        }
    }
    else if(!isLocationEnabled && permissions.allPermissionsGranted){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Please on Your Location to Enable this feature to work....")
        }
    }
    else{
        DisplayRationale(permisionState = permissions, rationale = listOf(stringResource(id = R.string.ACCESS_FINE_LOCATION),
            stringResource(id = R.string.ACCESS_COARSE_LOCATION)))
    }

}

@Composable

fun ServiceVocOnline(viewmodel: VocConnectViewModel, navController: NavController){
//    val locationManager = LocalContext.current.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//    locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val locationServices = LocationServices.getFusedLocationProviderClient(LocalContext.current)
   //locationServices.locationAvailability
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Surface(modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()) {
            if (!viewmodel.loadingState){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()) {
                    MapView(modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),locationServices =locationServices , viewModel = viewmodel, polyline = null,workers = viewmodel.thoseUserLatLng.value)
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp),modifier = Modifier.weight(1f)){
                        items(viewmodel.availableWorker){ item ->
                            WorkerCards(onClick = {viewmodel.selectUser(item.pos);navController.navigate(route = ScreenRoute.ServiceVocInfo.route)} ,profilePic = item.profilePic, name = item.name, distance = item.distance, ratings = item.rating, duration =item.duration )
                        }
                        item {
                            if(viewmodel.availableWorker.isEmpty()){
                                Box(contentAlignment = Alignment.Center ,modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .fillMaxHeight()) {
                                    Text(text = "No Available Worker.... Sorry!!!", fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }else{
                Dialog(onDismissRequest = { /*TODO*/ }) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}

@Composable
fun ServiceVocInfo(viewmodel: VocConnectViewModel, navController: NavController){
    val confirmationState = rememberSaveable {
        mutableStateOf(false)
    }
    var dismissprogress by rememberSaveable {
        mutableFloatStateOf(1f)
    }
    val dismissible = rememberSaveable {
       mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val config = LocalConfiguration.current
    if (confirmationState.value){
        Dialog(onDismissRequest = { if (dismissible.value) confirmationState.value= false}) {
            LaunchedEffect(key1 = true, block = {
                viewmodel.sendJobAlert()
                while (dismissprogress > 0){
                    delay(1000);
                    dismissprogress = dismissprogress.minus(0.01f)
                    if(viewmodel.alertSucessfull == AlertWorkerState.Failed){
                        dismissprogress = 0f
                    }
                }
                dismissible.value = true
            })
            ServiceWaitingConfirm(progress = dismissprogress, navController,viewmodel)
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Surface (modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()){
            Column(modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()) {
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier.weight(1f)){
                    item { Header(title = "Worker Detail")}
                    item { AsyncImage(modifier = Modifier
                        .size(200.dp)
                        .clip(MaterialTheme.shapes.medium),contentScale = ContentScale.Crop ,model = ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.jewelry).data(viewmodel.thatUser.profilePic).build(), contentDescription = "") }
                    item {AboutMe(aboutMe =viewmodel.thatUser.fullname , title ="Name" )  }
                    item { AboutMe(aboutMe = viewmodel.thatUser.address, title = "WorkAddress") }
                    item { AboutMe(aboutMe =viewmodel.thatUser.description , title = "AboutMe") }
                    item {
                        VideoAboutMyWork(uri = Uri.parse(viewmodel.thatUser.introVideo), title = "Video About My Work")
                    }
                    item {
                        MyPastWorkGallery(pastWork = viewmodel.thatUser.imageOfPrevWork, modifier = Modifier
                            .height(config.screenHeightDp.dp))
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) ,colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { confirmationState.value = true }) {
                    Text(text = "Proceed")
                }
            }
        }
    }
}


@Composable
fun ServiceSession(viewmodel: VocConnectViewModel, navController: NavController){
    val locationServices = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    val state = rememberSaveable {
        mutableStateOf(TransactionStage.AgreeStage)
    }
    Surface {
        Column(modifier = Modifier
            .systemBarsPadding()
            .navigationBarsPadding()) {
            MapView(modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),locationServices = locationServices, viewModel = viewmodel, polyline = viewmodel.polyline, workers =null)
            Surface(modifier = Modifier.weight(1f)) {
                when(state.value){
                    TransactionStage.AgreeStage -> {
                        AgreeBox(navController = navController,viewmodel = viewmodel, state)
                    }
                    TransactionStage.AgreedStage -> {
                        when(viewmodel.yourType){
                            ClientType.Client->{
                                ClientBox(viewModel = viewmodel, navController = navController, state = state)
                            }
                            ClientType.Worker -> {
                                VocBox(viewModel = viewmodel, navController,state)
                            }
                        }
                    }
                    TransactionStage.ReviewStage ->{
                        RateService(viewmodel = viewmodel, navController = navController)
                    }
                }
            }

        }
    }

}
@Composable
fun ServiceChat(viewmodel: VocConnectViewModel,navController: NavController){
    var textState by rememberSaveable {
        mutableStateOf("")
    }
    val headColor = if(!isSystemInDarkTheme()) colorResource(id = R.color.btn)else MaterialTheme.colorScheme.surfaceVariant
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = headColor)) {
        ConversationHeader(navController,modifier = Modifier
            .fillMaxHeight(0.2f)
            .statusBarsPadding(), viewModel = viewmodel)
        Surface(shape = RoundedCornerShape(topStartPercent = 12, topEndPercent = 12),color = MaterialTheme.colorScheme.background, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .align(Alignment.BottomCenter)) {
            Column {
                LazyColumn(modifier = Modifier
                    .navigationBarsPadding()
                    .weight(1f),contentPadding = PaddingValues(top = 16.dp, start = 4.dp)){
                    items(viewmodel.transactSession.transactionMsg){msg->
                        if(msg.uuid == viewmodel.uuid){
                            ChatRoom.Sender(msg = msg.msg,
                                SimpleDateFormat("hh:mma", Locale.getDefault()).format(msg.timestamp.toDate()))
                        }else{
                            ChatRoom.Reciever(painter = msg.profilePic, msg =msg.msg,
                                SimpleDateFormat("hh:mma", Locale.getDefault()).format(msg.timestamp.toDate()))
                        }
                    }

                }
                TextField(colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),shape = MaterialTheme.shapes.extraLarge,
                    trailingIcon ={ IconButton(onClick = {
                        viewmodel.transactSession.transactionMsg=
                            viewmodel
                                .transactSession
                                .transactionMsg
                                .toMutableList()
                                .also { it.add(UserMessage(viewmodel.thisUser.profilePic,textState,uuid=viewmodel.uuid))}
                        viewmodel.updateSession()
                        textState=""
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Rounded.Send, contentDescription = "")
                    }} ,modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(), placeholder = { Text(text = "Type a message")},
                    value = textState, onValueChange = {update->textState=update})

            }
        }
    }
}

@Composable
fun SearchRes(query: String, onClick:()->Unit){
    Row (horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick.invoke() }){
        IconButton(modifier = Modifier.clip(CircleShape),colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurfaceVariant), onClick = {onClick.invoke()}) {
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "")
        }
        Text(text = query, modifier = Modifier.weight(1f, true))
        IconButton(onClick = { onClick.invoke()}) {
            Icon(modifier = Modifier.rotate(-45f),imageVector = Icons.Rounded.ArrowUpward, contentDescription = "")
        }
    }
}



fun onSearch(viewmodel: VocConnectViewModel, navController: NavController){
    if(viewmodel.filteredVoc.isEmpty()) return
    viewmodel.updateQuery(viewmodel.filteredVoc[0])
    viewmodel.onSearch()
    navController.navigate(route = ScreenRoute.ServiceVocOnline.route)
}
@Composable
fun ConversationHeader(navController: NavController,modifier: Modifier,viewModel: VocConnectViewModel){
    Column(modifier = modifier) {
        IconButton(onClick = {navController.popBackStack() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "",
                tint = Color.White
            )
        }
        Row (verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.spacedBy(4.dp) ,modifier = Modifier.padding(horizontal = 16.dp)){
            Text(modifier = Modifier.weight(1f),text = viewModel.thatUser.fullname, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold, color = Color.White)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.PhoneInTalk, contentDescription = "",tint = Color.White)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.Videocam, contentDescription ="", tint = Color.White )
            }
        }
    }
}

@Composable
fun Header(title:String){
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .background(color = colorResource(id = R.color.btn))) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "",
                tint = Color.White
            )
        }
        Text(text = title, modifier = Modifier.weight(1f,true), textAlign = TextAlign.Center, color = Color.White, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Flows(items:List<String>, modifier:Modifier, onSearch: (query: String) -> Unit){
    val scrollState = rememberScrollState()
    FlowRow(verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)) {
        items.forEach { item->
            LazyGrids(item = item, onSearch = { q->onSearch(q)})
        }
    }
}
@Composable
fun LazyGrids(item:String, onSearch: (query: String) -> Unit){
    Surface(modifier = Modifier.clickable { onSearch(item) },shape = MaterialTheme.shapes.large,tonalElevation = 24.dp) {
        Text(text = item, modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(), fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCourseBtn(query: String,onQuery:(query:String)->Unit,modifier: Modifier, onSearch:(query:String)->Unit, items:List<String>){
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = active){onQuery("")}
    val padding = if (active)0.dp else 16.dp
    val trailing = if (active) Icons.AutoMirrored.Rounded.ArrowBack else Icons.Rounded.Search
    SearchBar(placeholder = { Text(text = "Search for vocational worker",
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        fontWeight = FontWeight.Medium)},
        leadingIcon = { IconButton(onClick = { active =!active }) {
            Icon(imageVector = trailing, contentDescription = "")
        }},
        modifier = modifier.padding(horizontal = padding, vertical = padding),
        query =query,
        onQueryChange =onQuery,
        onSearch =onSearch,
        active = active,
        onActiveChange = { update->active = update}) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp) ,contentPadding = PaddingValues(16.dp),state = rememberLazyListState()){
            items(items = items){
                SearchRes(query = it) { onSearch(it) }
            }
        }

    }
}







enum class VocationalCategory{
    Carpentry,
    Aluminium0Fabrication,
    Plumbing,
    Landscaping0and0Gardening,
    Painting,
    Ac0Maintainance,
    Catering,
    Automotive0Repair,
    Bricklayer,
    Knitting,
    ShoeMaker,
    Furniture0Restoration,
    Welding,
    Jewelry0Making,
    Appliance0Repair,
    Mechanic,
    Electrical0Installation,
    Fashion0Design,
    Salon,
    Photography
}
enum class TransactionStage{
    AgreeStage,
    AgreedStage,
    ReviewStage
}
enum class ClientType{
    Client,
    Worker
}

enum class AlertWorkerState{
    Init,
    Sucess,
    Failed
}