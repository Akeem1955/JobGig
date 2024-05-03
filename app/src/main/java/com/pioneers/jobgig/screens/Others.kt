package com.pioneers.jobgig.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.QuestionAnswer
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.UploadFile
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.pioneers.jobgig.R
import com.pioneers.jobgig.dataobj.utils.Comment
import com.pioneers.jobgig.dataobj.utils.CourseContent
import com.pioneers.jobgig.dataobj.utils.DonationRequest
import com.pioneers.jobgig.dataobj.utils.LatLngs
import com.pioneers.jobgig.sealed.HomeCardViews
import com.pioneers.jobgig.services.JobSearch
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.DashboardViewmodel
import com.pioneers.jobgig.viewmodels.DonateViewModel
import com.pioneers.jobgig.viewmodels.OnBoardViewModel
import com.pioneers.jobgig.viewmodels.ProfileViewmodel
import com.pioneers.jobgig.viewmodels.TutCreate
import com.pioneers.jobgig.viewmodels.VocConnectViewModel
import com.pioneers.jobgig.viewmodels.VocViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.util.UUID


@Composable
fun ServiceWaitingConfirm(progress:Float, navController: NavController, viewmodel: VocConnectViewModel){
    LaunchedEffect(key1 = viewmodel.transactSession){
        if(viewmodel.transactSession.initiated){
            viewmodel.getPolygonLine()
            navController.navigate(route = ScreenRoute.ServiceSession.route){
                popUpTo(ScreenRoute.ServiceSearch.route)
            }
        }
    }
    Surface(shape = MaterialTheme.shapes.large, tonalElevation = 24.dp) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(24.dp)) {
            Text(text = "Waiting For Worker To Accept Request", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
            LinearProgressIndicator(progress = { progress },strokeCap = StrokeCap.Round)
            if(progress <= 0){
                Text(text = "Worker Reject or Failed to Accept Request!", fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
@Composable
fun BalanceDash2(){
    Column(modifier = Modifier.fillMaxSize()) {
        Surface (modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),shape = MaterialTheme.shapes.large, tonalElevation = 24.dp, shadowElevation = 4.dp){
            Column(verticalArrangement = Arrangement.spacedBy(10.dp),modifier = Modifier.padding(16.dp)) {
               Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                   Column {
                       Text(text = "Add Money")
                       Box {
                           IconButton(onClick = { /*TODO*/ }) {
                               Icon(imageVector = Icons.Rounded.AccountBalanceWallet, contentDescription = "", tint = colorResource(
                                   id = R.color.btn
                               ))
                           }
                           Icon(imageVector = Icons.Rounded.AddCircleOutline, contentDescription = "", modifier = Modifier.align(
                               Alignment.BottomEnd), tint = colorResource(
                               id = R.color.btn))
                       }

                   }
                   Text(text = "Available Balance: $800", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
               }
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { /*TODO*/ }) {
                        Text(text = "Withdraw")
                    }
                }
            }
        }
    }
}



@Composable
fun BalanceDash(){
    var withdraw by rememberSaveable {
        mutableStateOf(false)
    }
    var deposit by rememberSaveable {
        mutableStateOf(false)
    }
    if (withdraw){
        Dialog(onDismissRequest = { withdraw = false }) {
            WithdrawComedy()
        }
    }
    if (deposit){
        Dialog(onDismissRequest = { deposit = false }) {
            NotImplemented()
        }
    }
    Column {
        Surface (modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp, shadowElevation = 6.dp){
            Column(verticalArrangement = Arrangement.spacedBy(24.dp),modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween ){
                    Text(text = "Available Balance:", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                   Row(verticalAlignment = Alignment.CenterVertically) {
                       Icon(painter = painterResource(id =R.drawable.tabler_currency_naira ), contentDescription = "", modifier = Modifier.size(16.dp))
                       Text(text = String.format("%,d", 10000))
                   }
                }
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { deposit = true }) {
                        Text(text = "Deposit")
                    }
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { withdraw = true }) {
                        Text(text = "Withdraw")
                    }
                }
            }
        }
    }
}


@Composable
fun TutCard(uri:String ="",label: String="",index:Int =0,viewmodel: TutCreate? = null, deletable:Boolean = false){
    Surface(shadowElevation = 4.dp,tonalElevation = 24.dp,shape = MaterialTheme.shapes.large,modifier = Modifier
        .fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            AsyncImage(modifier = Modifier
                .size(72.dp)
                .clip(MaterialTheme.shapes.medium),contentScale = ContentScale.FillBounds,model = ImageRequest.Builder(LocalContext.current).decoderFactory(VideoFrameDecoder.Factory()) .placeholder(R.drawable.round_image_24).data(uri).error(R.drawable.round_image_24).build(), contentDescription = "")
            Column(modifier = Modifier.weight(1f,true)) {
                Text(text = label, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
                if (deletable){
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                        IconButton(onClick = { viewmodel?.delete(index)}) {
                            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TutUpload(openState: MutableState<Boolean>, viewmodel: TutCreate,context: Context){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {uri ->
        if (uri != null){
            viewmodel.newTutUri.value = uri.toString()
            viewmodel.newTutTitle.value=textState.value
            viewmodel.upload(context)
            openState.value=false
        }
        else{
            viewmodel.errorMsg="Unable to pick media might be corrupted"
            viewmodel.errorState=true
            openState.value = false
        }

    })
    Dialog(onDismissRequest = { openState.value=false }) {
        Surface(tonalElevation = 32.dp, shape = MaterialTheme.shapes.extraLarge) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
               Row {
                   Text(text ="Note:", fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold )
                   Text(text = "each video should not exceed 15 minute", fontSize = MaterialTheme.typography.labelMedium.fontSize)
               }
                CustomTextField(modifier = Modifier, type =TextType.Edit, placeholder = "response here...", label = "Title", textState = textState)
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                }) {
                    Text(text = "Upload Tutorial")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageTuts(viewmodel: TutCreate,navController: NavController){
    val ctx = LocalContext.current
    val uploadState = rememberSaveable {
        mutableStateOf(false)
    }
    val searchState = rememberSaveable {
        mutableStateOf(false)
    }
    val queryState = rememberSaveable {
        mutableStateOf("")
    }
    if (uploadState.value){
        TutUpload(openState = uploadState,viewmodel,ctx)}
    if(viewmodel.loadingState){
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }
    if(viewmodel.errorState){
        Dialog(onDismissRequest = { viewmodel.errorState = false }) {
            Surface(shape = MaterialTheme.shapes.large) {
                Text(text = viewmodel.errorMsg, modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            }
        }
    }

    LaunchedEffect(key1 = true){
        viewmodel.getTutorial()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()) {
            IconButton(onClick = { if(navController.canGoBack)navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
            Text(modifier = Modifier.weight(1f,true),
                text = "Tutorial Dashboard",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.Bold)
        }
        Surface {
           Column(modifier = Modifier
               .fillMaxSize()
               .navigationBarsPadding()) {
               SearchBar(modifier = Modifier.padding(horizontal = 4.dp),placeholder = { Text(text = "Search Your Tutorial")},
                   leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = "")},
                   query = queryState.value,
                   onQueryChange = {update->queryState.value = update},
                   onSearch = {viewmodel.filter(queryState.value);searchState.value=false},
                   active = searchState.value, onActiveChange = {update->searchState.value=update}) {
               }
               LazyColumn(contentPadding = PaddingValues(8.dp),verticalArrangement = Arrangement.spacedBy(16.dp)) {
                   item {
                       Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth( )) {
                           Text(text = "Add new tutorial")
                           IconButton(onClick = {uploadState.value = true}) {
                               Icon(imageVector = Icons.Rounded.AddCircleOutline, contentDescription ="" )
                           }
                       }
                   }
                   itemsIndexed(viewmodel.tutorial){index,item->
                       TutCard(uri = item.uri, label = item.title,index=index, deletable = true, viewmodel = viewmodel)
                   }

               }
           }
        }
    }
}

@Composable
fun TutItem(label: String, onClick:()->Unit){
    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
        Text(text = label)
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "")}
    }
}

@Composable

fun CreateTuts(viewmodel:TutCreate, navController: NavController){

    val thubnailStatus = rememberSaveable {
        mutableStateOf(false)
    }
    val photopick = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult ={uri->
        if (uri != null)viewmodel.thumburi.value = uri.toString()
    } )
    var label by rememberSaveable {
        mutableStateOf("Requirement")
    }
    val contentPickStatus = rememberSaveable {
        mutableStateOf(false)
    }
    val contentOtherStaus = rememberSaveable {
        mutableStateOf(false)
    }
    if(contentPickStatus.value){
        TutContent(openState = contentPickStatus,viewmodel)
    }
    if(contentOtherStaus.value){
        TutContent(openState = contentOtherStaus,label,when(label){
            "Requirement"->viewmodel.requirement
            "Aim of This Tutorial"->viewmodel.aim
            "Who is This Tutorial for"->viewmodel.who
            else-> emptyList<String>().toMutableStateList()
        })
    }
    if(thubnailStatus.value){
        TutContent(openState = thubnailStatus, uri =Uri.parse(viewmodel.thumburi.value))
    }
    if(viewmodel.loadingState){
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }
    if(viewmodel.errorState){
        Dialog(onDismissRequest = { viewmodel.errorState = false }) {
            Surface(shape = MaterialTheme.shapes.large) {
                Text(text = viewmodel.errorMsg, modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            }
        }
    }
    val ctx = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()) {
            IconButton(onClick = { if(navController.canGoBack)navController.popBackStack()  }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Text(textAlign = TextAlign.Center,modifier = Modifier.weight(1f),text = "Create Tutorial", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .weight(1f, true)
            .navigationBarsPadding()) {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)){
                item {
                   CustomTextField(modifier = Modifier, label = "Tutorial Name", placeholder = "A.Vondi",type = TextType.Edit, textState = viewmodel.tutName)
               }
                item{
                    CustomTextField(modifier = Modifier, label = "Describe Yourself", placeholder = "I Am A Diligent Skillfully Vocational Worker",type = TextType.Person, textState = viewmodel.about)
                }
                item {
                    CustomTextField(modifier = Modifier, label = "Describe Tutorial", placeholder = "This tutorial covers everything from basic stitches to advanced patterns.",type = TextType.Edit, textState = viewmodel.tutDes)
                }
                item {
                    Column {
                        Text(text = " Tutorial Thumbnail")
                        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                            Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                                id = R.color.btn
                            ), contentColor = Color.White),shape = MaterialTheme.shapes.medium,onClick = { photopick.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            ) }) {
                                Text(text = "Upload")
                            }
                            if (viewmodel.thumburi.value.isNotBlank()){
                                IconButton(onClick = { thubnailStatus.value = true }) {
                                    Icon(imageVector = Icons.Rounded.UploadFile, contentDescription = "")
                                }
                            }
                        }
                    }
                }
                item {
                   TutItem(label = "Requirement", onClick = {
                       label = "Requirement"
                       contentOtherStaus.value= true
                   })
                }
                items(viewmodel.requirement){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(modifier = Modifier.size(12.dp),imageVector = Icons.Rounded.Circle, contentDescription = "")
                        Text(text = it, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }
                }
                item {
                    TutItem(label = "Who is This Tutorial for", onClick = {
                        label = "Who is This Tutorial for"
                        contentOtherStaus.value= true
                    })
                }
                items(viewmodel.who){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(modifier = Modifier.size(12.dp),imageVector = Icons.Rounded.Circle, contentDescription = "")
                        Text(text = it, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }
                }
                item {
                    TutItem(label = "Aim of This Tutorial", onClick = {
                        label = "Aim of This Tutorial"
                        contentOtherStaus.value= true
                    })
                }
                items(viewmodel.aim){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(modifier = Modifier.size(12.dp),imageVector = Icons.Rounded.Circle, contentDescription = "")
                        Text(text = it, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    }
                }
                item {
                    TutItem(label = "Add Tutorial Content", onClick = {
                        contentPickStatus.value= true
                    })
                }
                items(viewmodel.tutContent){
                    TutCard(it.uri, it.title)
                }
                item {
                    Button(modifier = Modifier.fillMaxWidth(),colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.medium,onClick = { viewmodel.createTut(ctx,navController) }) {
                        Text(text = "Create Tutorial")
                    }
                }

            }
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VocationalDashboard(navController: NavController, viewmodel: DashboardViewmodel){
    val ctx = LocalContext.current
    val locationServices = LocationServices.getFusedLocationProviderClient(LocalContext.current)
    val permissions = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION) )
    val timeline = rememberSaveable {
        mutableStateOf(TimeLine.Daily)
    }
    Column{
        Surface {
           if (permissions.allPermissionsGranted){
               LaunchedEffect(key1 = true){
                   try {
                       val realLocation = locationServices.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null).await()
                       OnBoardViewModel.currentUser.value .currentLocation = LatLngs(realLocation.latitude,realLocation.longitude)
                   }catch (e:Exception){
                       println(e.message)
                       e.printStackTrace()
                   }
               }
               LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                   .fillMaxSize()
                   .padding(bottom = 80.dp)
                   .navigationBarsPadding()){
                  item { Row (horizontalArrangement = Arrangement.spacedBy(4.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                      .fillMaxWidth()
                      .padding(8.dp)){
                      AsyncImage(contentScale = ContentScale.Crop,
                          modifier = Modifier
                              .size(50.dp)
                              .clip(CircleShape),
                          model = ImageRequest.Builder(LocalContext.current).data(OnBoardViewModel.currentUser.value.profilePic).error(R.drawable.round_account_circle_24).build(),
                          contentDescription = "")
                      Text(text = "Hi, ${OnBoardViewModel.currentUser.value .fullname}", modifier = Modifier.weight(1f,true), fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold)
                      Text(text = "${if(OnBoardViewModel.currentUser.value.tutCreated)"Upload" else "Create"} Tutorial",fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold, color = colorResource(
                          id = R.color.btn
                      ))
                      IconButton(onClick = {navController.navigate(if(OnBoardViewModel.currentUser.value.tutCreated)ScreenRoute.UploadTutorial.route else ScreenRoute.CreateTutorial.route)  }) {
                          Icon(
                              imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                              contentDescription = "",
                              tint = colorResource(
                                  id = R.color.btn
                              )
                          )
                      }

                  }}
                   item { Row(modifier = Modifier.padding(horizontal = 16.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                       Text(text = "Online", color = colorResource(id = R.color.btn))
                       Switch(checked = onlineState, onCheckedChange ={update->
                           println("online state is $update" )
                           OnBoardViewModel.currentUser.value.online = update
                           onlineState = update
                           if (update){
                               viewmodel.updateUserState()
                               println("Omo the update is $update what happen then")
                               val intent = Intent(ctx, JobSearch::class.java)
                               ctx.startService(intent)
                           }else{
                               val intent = Intent(ctx, JobSearch::class.java)
                               intent.action="online_stop"
                               ctx.startService(intent)
                           }
                       })
                   }
                   }
                   item { Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                       TimelineHelper(timeline = timeline, type = TimeLine.Daily, text ="Daily" )
                       TimelineHelper(timeline = timeline, type = TimeLine.Monthly, text ="Monthly" )
                       TimelineHelper(timeline = timeline, type = TimeLine.ALlTime, text ="All Time" )
                   }
                   }
                   item { LazyVerticalGrid(modifier = Modifier.size(350.dp),horizontalArrangement = Arrangement.spacedBy(16.dp),verticalArrangement = Arrangement.spacedBy(16.dp),contentPadding = PaddingValues(16.dp),columns = GridCells.Adaptive(150.dp)){
                       item { TimelineCard(label = "${viewmodel.data.value.content.size}", content = "Total Tutorials") }
                       item { TimelineCard(label = "${viewmodel.data.value.learners}", content = "Total Subscribed") }
                       item { TimelineCard(label = "${viewmodel.data.value.rating}", content = "OverAll Rating") }
                       item { TimelineCard(label = "${viewmodel.data.value.comments.size}", content = "Total Comments") }
                   }
                   }
                   item {HomeCardView(type = HomeCardViews.Courses, navController = navController, route =ScreenRoute.HomeScreenCourse.route ) }

                   item { Spacer(modifier = Modifier.height(16.dp)) }
               }
           }
           else{
               DisplayRationale(permisionState = permissions, rationale = listOf(
                   stringResource(id = R.string.ACCESS_FINE_LOCATION),
                   stringResource(id = R.string.ACCESS_COARSE_LOCATION)
               ))
           }
        }
    }
}

@Composable
fun TimelineHelper(timeline:MutableState<TimeLine>, type:TimeLine, text:String){
    if(timeline.value.name == type.name) Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
        id = R.color.btn
    ), contentColor = Color.White),onClick = { /*TODO*/ }) {
        Text(text = text)
    }else TextButton(onClick = { timeline.value = type}) {
        Text(text = text)
    }
}

@Composable
fun TimelineCard(label: String, content:String){
    Surface(modifier = Modifier.size(150.dp), tonalElevation = 20.dp, shadowElevation = 4.dp, shape = MaterialTheme.shapes.medium) {
        Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxSize()) {
            Text(text = label)
            Text(text = content)
        }
    }
}

@Composable
fun RequestVerification(viewmodel:VocViewmodel,navController: NavController){
    val ctx = LocalContext.current
    if(viewmodel.loadingState){
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }
    if(viewmodel.errorState){
        Dialog(onDismissRequest = { viewmodel.errorState = false }) {
            Surface(shape = MaterialTheme.shapes.large) {
                Text(text = viewmodel.errorMsg, modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()) {
            IconButton(onClick = { if(navController.canGoBack)navController.popBackStack()  }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Text(textAlign = TextAlign.Center,modifier = Modifier.weight(1f),text = "Request Verification", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Surface(modifier = Modifier
            .weight(1f, true)
            .fillMaxWidth(), shape = RoundedCornerShape(topStart = 24.dp , topEnd = 24.dp)) {
            LazyColumn(contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()) {
                item {
                    CustomTextField(modifier = Modifier.padding(horizontal = 16.dp), label = "Phone Number", placeholder = "+2349064805505",type = TextType.Phone,textState = viewmodel.phone)
                }
                item {
                    CustomTextField(modifier = Modifier.padding(horizontal = 16.dp), label = "About You", placeholder = "I am a decent person",type = TextType.Person,textState = viewmodel.about)
                }
                item {
                    CustomTextField(modifier = Modifier.padding(horizontal = 16.dp), label = "Work Address", placeholder = "1, Baamlong Street",type = TextType.Address,textState = viewmodel.address)
                }
                item {
                    Drop(modifier = Modifier.padding(horizontal = 16.dp), items = enumValues<VocationalCategory>().map {it.name.replace("0"," ")}, state =viewmodel.type )
                }
                item { HorizontalDivider(); }
                item {
                    var pickError by rememberSaveable {
                        mutableStateOf(false)
                    }
                    var picked by rememberSaveable {
                        mutableStateOf(false)
                    }
                    val photoPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {uri->
                        if(uri != null){picked = true;viewmodel.videoUri.value=uri.toString()}else {pickError = true;picked=false}
                    } )
                    if (pickError){
                        Dialog(onDismissRequest = { pickError = false }) {
                            Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp) {
                                Text(text = "Error happen when picking try picking again", modifier = Modifier.padding(24.dp), color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        Text(text = "Upload a 30s video about your work")
                       Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                           Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                               id = R.color.btn
                           ), contentColor = Color.White),shape = MaterialTheme.shapes.medium,onClick = { photoPicker.launch(
                               PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                           ) }) {
                               Text(text = "Upload")
                           }
                          if (picked){
                              Icon(imageVector = Icons.Rounded.UploadFile, contentDescription = "")
                          }
                       }
                    }
                }
                item { HorizontalDivider() }
                item {
                    var pickError by rememberSaveable {
                        mutableStateOf(false)
                    }
                    val photoPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(), onResult = {uris->
                        if(uris.isNotEmpty()){
                            viewmodel.gallery = uris.map {
                                it.toString()
                            }
                        }else pickError = true
                    } )
                    if (pickError){
                        Dialog(onDismissRequest = { pickError = false }) {
                            Surface(shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp) {
                                Text(text = "No Picture Was Selected", modifier = Modifier.padding(24.dp), color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        Text(text = "Upload images showcasing your previous work as a vocational worker. Include photos that highlight the quality and variety of your skills.")
                        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                            Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                                id = R.color.btn
                            ), contentColor = Color.White),shape = MaterialTheme.shapes.medium,onClick = { photoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            ) }) {
                                Text(text = "Pick Photos")
                            }
                        }
                    }
                }
                item {
                    MyPastWorkGallery(pastWork = viewmodel.gallery, modifier = Modifier
                        .heightIn(max = 700.dp)
                        .fillMaxWidth())
                }
                item { HorizontalDivider() }
                item{
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.medium,onClick = { viewmodel.requestVerification(ctx,navController)}) {
                        Text(text = "Request Verification")
                    }
                }

            }
        }
    }
}

@Composable
fun DonateAsk(viewModel: DonateViewModel,navController: NavController){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    val type = rememberSaveable {
        mutableStateOf(DonateType.CraftSpace.name)
    }
    var notQualify by rememberSaveable {
        mutableStateOf(false)
    }
    if (notQualify){
        Dialog(onDismissRequest = { notQualify = false}) {
            Surface(shape = MaterialTheme.shapes.large, tonalElevation = 32.dp) {
                Text(text = "You are Not A Vocational Worker...",modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            }
        }
    }
    if (viewModel.sucessState){
        Dialog(onDismissRequest = { viewModel.sucessState = false}) {
            LaunchedEffect(key1 = true){
                delay(2000)
                navController.popBackStack()
            }
            Surface(shape = MaterialTheme.shapes.large, tonalElevation = 32.dp) {
                Text(text = "Your request have been sucessfully added",modifier = Modifier.padding(16.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize)
            }
        }
    }
    if (viewModel.loadingState){
        Dialog(onDismissRequest = { }) {
            CircularProgressIndicator()
        }
    }
    if (viewModel.errorState){
        Dialog(onDismissRequest = { viewModel.errorState = false }) {
            Surface (shape = MaterialTheme.shapes.large, tonalElevation = 32.dp){
                Text(text = viewModel.errorMsg)
            }

        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()) {
            IconButton(onClick = {if(navController.canGoBack) navController.popBackStack()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
            Text(textAlign = TextAlign.Center,modifier = Modifier.weight(1f),text = "Request For Vocational Support", fontWeight = FontWeight.Bold, color = Color.White)
        }

        Surface(modifier = Modifier
            .fillMaxSize(),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
               CustomTextField(modifier = Modifier, type = TextType.Reason, placeholder = "Your answer here...", label = "Reason For Assistance", textState = textState)
                Text(text = "Request Type:", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold)
                Drop(modifier = Modifier, items = enumValues<DonateType>().map { it.name }, type)
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = {
                    val req = DonationRequest()
                    req.name=OnBoardViewModel.currentUser.value.fullname
                    req.uid = UUID.randomUUID().toString()
                    req.profile= OnBoardViewModel.currentUser.value.profilePic
                    req.reason=textState.value
                    if(type.value == DonateType.HavenFund.name||type.value== DonateType.SkillForgeAid.name){
                        req.target = viewModel.targets[type.value] ?:100000.0
                        req.type=type.value
                    }
                    if (OnBoardViewModel.currentUser.value.verified)viewModel.addDonationReq(req)else notQualify = true
                }) {
                    Text(text = "Submit Request")
                }
            }
        }
    }
}
@Composable
fun DonateGiveCard(type:DonateType, onClick: () -> Unit,req:DonationRequest){
    var showMore by rememberSaveable {
        mutableStateOf(true)
    }
    val fundType by rememberSaveable {
        mutableStateOf(type)
    }
    val drop = if(!showMore)Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown
    Surface(modifier = Modifier.padding(horizontal = 16.dp),shape = MaterialTheme.shapes.extraLarge, tonalElevation = 32.dp, shadowElevation = 6.dp) {
        when(fundType){
            DonateType.CraftSpace -> {
                Column(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()) {
                    Row (horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()){
                        AsyncImage(modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current).data(req.profile).error(R.drawable.round_account_circle_24).build(),
                            contentDescription = "",
                            contentScale = ContentScale.Crop)
                        Text(text = "From, ${req.name}", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.animateContentSize()){
                        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Reason for Assistance: ")
                            IconButton(onClick = { showMore = !showMore }) {
                                Icon(imageVector = drop, contentDescription = "")
                            }
                        }
                        if (showMore){
                            Text(text = req.reason)
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                        Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                            id = R.color.btn
                        ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick =  onClick) {
                            Text(text = "Provide WorkSpace")
                        }
                    }
                }
            }
            else ->{
                Column(verticalArrangement = Arrangement.spacedBy(4.dp),modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()) {
                    Row (horizontalArrangement = Arrangement.spacedBy(8.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier.fillMaxWidth()){
                        AsyncImage(modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current).data(req.profile).error(R.drawable.round_account_circle_24).build(),
                            contentDescription = "",
                            contentScale = ContentScale.Crop)
                        Text(text = "From, ${req.name}", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                    }
                    Column(modifier = Modifier.animateContentSize()){
                        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Reason for Assistance: ",fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { showMore = !showMore }) {
                                Icon(imageVector = drop, contentDescription = "")
                            }
                        }
                        if (showMore){
                            Text(text = req.reason,fontSize = MaterialTheme.typography.bodySmall.fontSize)
                        }
                    }
                    Row {
                        Text(text = "Target: ",fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                        Text(text = "${req.target}",fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Funded ${(req.raised/req.target) * 100}%",fontSize = MaterialTheme.typography.labelSmall.fontSize, fontWeight = FontWeight.Bold)
                        LinearProgressIndicator(modifier = Modifier.height(8.dp),progress = {(req.raised/req.target).toFloat()}, strokeCap = StrokeCap.Round)
                    }
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                        Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                            id = R.color.btn
                        ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = onClick) {
                            Text(text = "Donate")
                        }
                    }
                }
            }


        }
    }
}

@Composable
fun DonateGive(viewModel: DonateViewModel,navController: NavController, type:DonateType){
    val openState = rememberSaveable {
        mutableStateOf(false)
    }
    val donateReq = remember {
        mutableStateOf(DonationRequest())
    }
    if (openState.value){
        Dialog(onDismissRequest = { }) {
            if(type.name == DonateType.CraftSpace.name){
                ProvideWorkspace(openState =openState , viewModel =viewModel , donationRequest =donateReq.value )
            }
            else{
                DonateMoney(openState = openState, viewModel =viewModel , donationRequest = donateReq.value)
            }
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Row(modifier = Modifier.statusBarsPadding(),verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if (navController.canGoBack)navController.popBackStack() }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
            Text(text = "Help A Vocational Worker", fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold)
        }
        Surface(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)){
                item { Spacer(modifier = Modifier) }
                items(when(type)
                {
                    DonateType.CraftSpace->viewModel.donationsWorkspace.value
                    else ->viewModel.donations.value
                }){
                    DonateGiveCard(type = type, req = it, onClick = {
                        donateReq.value =it
                        openState.value=true
                    })
                }
                item { if(type.name != DonateType.CraftSpace.name && viewModel.donations.value.isEmpty() || type.name == DonateType.CraftSpace.name && viewModel.donationsWorkspace.value.isEmpty()){
                    Text(text = "No available donation request for now!!!", color = colorResource(id = R.color.btn), fontWeight = FontWeight.Bold)
                } }
            }
        }
    }
}
@Composable
fun DonateWhy(navController: NavController){
    val whyitwork = stringArrayResource(id = R.array.why_it_works)
    val whyitworkHeader = stringArrayResource(id = R.array.header)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Row(modifier = Modifier.statusBarsPadding(),verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { if(navController.canGoBack)navController.popBackStack()}) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "")
            }
            Text(text = "Donation Program", fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold)
        }
        Surface(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            LazyColumn(modifier = Modifier.navigationBarsPadding(),contentPadding = PaddingValues(vertical = 16.dp),verticalArrangement = Arrangement.spacedBy(16.dp)){
                item { HomeCardView(type = HomeCardViews.DonateMoney, navController = navController, route =ScreenRoute.DonateGive.route(DonateType.SkillForgeAid.name) ) }
                item { HomeCardView(type = HomeCardViews.ProvideWorkspace, navController = navController, route = ScreenRoute.DonateGive.route(DonateType.CraftSpace.name)) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(text = "Why Your Donation Works", fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), color = colorResource(id = R.color.btn))
                }
                itemsIndexed(whyitworkHeader){index,value->
                    Column(modifier = Modifier.padding(horizontal = 8.dp),verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = value, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
                        Text(text = whyitwork[index], fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }
}

@Composable
fun Donate(navController: NavController){
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            Text(text = "Donation Program", fontSize = MaterialTheme.typography.labelLarge.fontSize, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Surface(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            LazyColumn(modifier = Modifier.padding(16.dp),verticalArrangement = Arrangement.spacedBy(16.dp)){
                item { HomeCardView(type = HomeCardViews.Donate, navController = navController, route =ScreenRoute.DonateWhy.route ) }
                item { HomeCardView(type = HomeCardViews.DonateAsk, navController = navController, route = ScreenRoute.DonateSeek.route) }

            }
        }
    }
}

@Composable
fun Needs(modifier: Modifier){
    Column(modifier = modifier) {
        Text(text = "Reason for Assistance", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)) {
            TextField(colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent),shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth(),value = "Hi", onValueChange = {})
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drop(modifier: Modifier, items:List<String>, state:MutableState<String>){

    var menuState by rememberSaveable {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(modifier = modifier,expanded = menuState, onExpandedChange = {menuState= !menuState}) {
       OutlinedTextField(modifier = Modifier
           .fillMaxWidth()
           .menuAnchor(),
            readOnly = true,
           shape = MaterialTheme.shapes.medium,
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
            value = state.value,
            onValueChange ={},
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuState)})
        ExposedDropdownMenu(expanded = menuState, onDismissRequest = { menuState = false }) {
            items.forEach {
                DropdownMenuItem(text = { Column {
                    Text(text = it, fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold)
                    Text(
                        text = when (it) {
                            DonateType.SkillForgeAid.name->{"Providing help with acquiring vocational tools."}
                            DonateType.HavenFund.name->{"Support vocational workers with their renting fees"}
                            DonateType.CraftSpace.name->{"Assistance for securing workspaces."}
                            else->{""}
                        },
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                } },
                    onClick = { state.value = it;menuState=false})
            }
            
        }
    }

}

@Composable
fun CustomTextField(label:String = "",modifier: Modifier, placeholder:String = "",type:TextType,textState: MutableState<String>){

    val trailingIcon =
        when (type) {
            TextType.Email -> {
                Icons.Rounded.Email
            }

            TextType.Phone -> {
                Icons.Rounded.Phone
            }

            TextType.Person -> {
                Icons.Rounded.Person
            }

            TextType.Address -> {Icons.Rounded.Place}
            TextType.Edit->Icons.Rounded.Edit
            TextType.Reason -> Icons.Rounded.QuestionAnswer
            TextType.Number -> {ImageVector.vectorResource(id = R.drawable.tabler_currency_naira)}
        }

    val keyboardType = when(type){
        TextType.Email -> KeyboardType.Email
        TextType.Phone -> KeyboardType.Phone
        TextType.Number-> KeyboardType.Decimal
        else -> KeyboardType.Text
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp) ,modifier = modifier) {
        Text(text = "$label:", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.labelMedium.fontSize)
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp), shape = MaterialTheme.shapes.medium) {
            TextField(leadingIcon ={ Icon(imageVector = trailingIcon,
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = colorResource(id = R.color.btn))}
                ,keyboardOptions = KeyboardOptions(keyboardType = keyboardType) , placeholder = { Text(text = placeholder, fontSize = MaterialTheme.typography.labelSmall.fontSize) }, value = textState.value, onValueChange = { update->textState.value=update},colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent))
        }
    }
}





enum class DonateType{
    HavenFund,
    CraftSpace,
    SkillForgeAid
}

enum class TextType{
    Email,
    Phone,
    Person,
    Address,
    Number,
    Edit,
    Reason

}
enum class TimeLine{
    Daily,
    Monthly,
    ALlTime
}








@Composable
fun TutContent(openState: MutableState<Boolean>, label: String,data:SnapshotStateList<String>){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { openState.value=false }) {
        Surface(tonalElevation = 32.dp, shape = MaterialTheme.shapes.extraLarge) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                CustomTextField(modifier = Modifier, type =TextType.Edit, placeholder = "response here...", label = label, textState = textState)
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = {
                    data.add(textState.value)
                    openState.value=false
                }) {
                    Text(text = "Add Content")
                }
            }
        }
    }
}
@Composable
fun TutContent(openState: MutableState<Boolean>,viewmodel: TutCreate){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    val uri = rememberSaveable {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {
        if (it!=null){
            uri.value=it.toString()
            viewmodel.tutContent.add(CourseContent(title = textState.value, uri =uri.value,"forever"))
            openState.value=false
        }
    })
    Dialog(onDismissRequest = { openState.value=false }) {
        Surface(tonalElevation = 32.dp, shape = MaterialTheme.shapes.extraLarge) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row {
                    Text(text ="Note:", fontSize = MaterialTheme.typography.labelMedium.fontSize, fontWeight = FontWeight.Bold )
                    Text(text = "each video should not exceed 15 minute", fontSize = MaterialTheme.typography.labelMedium.fontSize)
                }
                CustomTextField(modifier = Modifier, type =TextType.Edit, placeholder = "response here...", label = "Title", textState = textState)
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                }) {
                    Text(text = "Add Content")
                }
            }
        }
    }
}


@Composable
fun TutContent(openState: MutableState<Boolean>, uri: Uri){
    Dialog(onDismissRequest = { openState.value=false }) {
        Surface(tonalElevation = 32.dp, shape = MaterialTheme.shapes.extraLarge) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                AsyncImage(modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.FillBounds,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uri)
                        .error(R.drawable.round_image_24)
                        .build(),
                    contentDescription ="" )
            }
        }
    }
}


@Composable
fun ProfileEdit(openState: MutableState<Boolean>, label: String,viewmodel: ProfileViewmodel, type:Int,ctx:Context){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    Dialog(onDismissRequest = { openState.value=false }) {
        Surface(tonalElevation = 32.dp, shape = MaterialTheme.shapes.extraLarge) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                CustomTextField(modifier = Modifier, type =TextType.Edit, placeholder = "response here...", label = label, textState = textState)
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = {
                    when(type){
                        0->viewmodel.name = textState.value
                        1->viewmodel.about = textState.value
                        2->viewmodel.address = textState.value
                    }
                    viewmodel.saveProfile(ctx)
                    openState.value=false
                }) {
                    Text(text = "Add Content")
                }
            }
        }
    }
}

@Composable
fun ProvideWorkspace(openState: MutableState<Boolean>,viewModel: DonateViewModel, donationRequest: DonationRequest){
    val textState = rememberSaveable {
        mutableStateOf("")
    }
    val textStateB = rememberSaveable {
        mutableStateOf("")
    }
    val textStateC = rememberSaveable {
        mutableStateOf("")
    }
    Surface(tonalElevation = 24.dp, shape = MaterialTheme.shapes.large) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Note: The workspace will be verified!")
            CustomTextField(modifier = Modifier, type =TextType.Address,
                textState = textState,
                label = "Location",
                placeholder = "Address of the workspace")
            CustomTextField(modifier = Modifier, type = TextType.Email, textState =textStateB, label = "Email", placeholder = "your email for further verification" )
            CustomTextField(modifier = Modifier, type = TextType.Phone, textState = textStateC,label = "Phone", placeholder = "your phone number here...")
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceAround) {
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn),
                    contentColor = Color.White),shape = MaterialTheme.shapes.large,
                    onClick = {
                        donationRequest.spacemail = textStateB.value
                        donationRequest.spaceaddress = textState.value
                        donationRequest.spacephone = textStateC.value
                        viewModel.updateDonationRequest(donationRequest.uid,donationRequest)
                        openState.value=false
                    }) {
                    Text(text = "Done")
                }
                Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError),shape = MaterialTheme.shapes.large,onClick = {
                        openState.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Composable
fun DonateMoney(openState: MutableState<Boolean>, viewModel: DonateViewModel, donationRequest: DonationRequest){
    val textState = rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = textState.value){
     try {
         textState.value.toDouble()
     }catch (e:Exception){
         println("Some user are funny thank god we handle this")
         textState.value= textState.value.filter {
             it.isDigit()
         }
     }
    }
    Surface(tonalElevation = 24.dp, shape = MaterialTheme.shapes.large) {
        Column(modifier = Modifier.padding(16.dp),verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(color = MaterialTheme.colorScheme.error,text = "once you click the pay button it will be deducted from your available balance", fontSize = MaterialTheme.typography.labelMedium.fontSize, lineHeight = MaterialTheme.typography.labelMedium.lineHeight)
            CustomTextField(modifier = Modifier, type =TextType.Number,
                textState = textState,
                label = "Amount",
                placeholder = "Enter the amount you want to donate")
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceAround) {
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn),
                    contentColor = Color.White),shape = MaterialTheme.shapes.large,
                    onClick = {
                        donationRequest.raised = donationRequest.raised + textState.value.toDouble()
                        viewModel.updateDonationRequest(donationRequest.uid,donationRequest)
                        openState.value = false
                    }) {
                    Text(text = "Done")
                }
                Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError),shape = MaterialTheme.shapes.large,
                    onClick = {
                        openState.value=false
                }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Composable
fun  Notifications(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))){
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()) {
            IconButton(onClick = { if(navController.canGoBack)navController.popBackStack()}) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "", tint = Color.White)
            }
            Text(text = "Notification",  textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = Color.White)
        }
        Surface(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "No Notification Yet")
            }
        }
    }
}

@Composable
fun NotImplemented(){
    Surface(tonalElevation = 24.dp, shape = MaterialTheme.shapes.large) {
        Text(modifier = Modifier.padding(32.dp),text = stringResource(id = R.string.deposit_comedy), fontSize = MaterialTheme.typography.labelMedium.fontSize, lineHeight = MaterialTheme.typography.labelMedium.lineHeight)
    }
}
@Composable
fun WithdrawComedy(){
    Surface(tonalElevation = 24.dp, shape = MaterialTheme.shapes.large) {
        Text(modifier = Modifier.padding(32.dp),text = stringResource(id = R.string.withdraw_comedy), fontSize = MaterialTheme.typography.labelMedium.fontSize, lineHeight = MaterialTheme.typography.labelMedium.lineHeight)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateService(viewmodel: VocConnectViewModel,navController: NavController){
    LaunchedEffect(key1 = true, block = {viewmodel.loadingState=false})
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var open by rememberSaveable {
        mutableStateOf(true)
    }
    val rating = rememberSaveable {
        mutableDoubleStateOf(5.0)
    }
    val review = rememberSaveable {
        mutableStateOf("")
    }
    var reviewCheck by rememberSaveable {
        mutableStateOf(false)
    }
    if (reviewCheck){
        Dialog(onDismissRequest = { reviewCheck = false}) {
            Surface(shape = MaterialTheme.shapes.large, tonalElevation = 24.dp) {
                Text(
                    text = "Rating and comment needed for user review",
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
    if (viewmodel.loadingState){
        Dialog(onDismissRequest = { /*TODO*/ }) {
            CircularProgressIndicator()
        }
    }

    if(open){
        ModalBottomSheet(onDismissRequest = { println("Dismiss request")
            navController.navigate(ScreenRoute.HomeEntry.route){
                popUpTo(ScreenRoute.HomeEntry.route){
                    inclusive=true
                }
            }
            open = false }, sheetState = sheetState) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .verticalScroll(rememberScrollState())
                    .padding(32.dp)) {
                Text(text = "How was your experience?", fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
                AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(viewmodel.thatUser.profilePic)
                    .error(R.drawable.round_account_circle_24)
                    .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentDescription = "")
                OldRatingBar(state = rating )
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                    placeholder = { Text(text = "Write your review")},
                    value =review.value ,
                    onValueChange ={ update->review.value=update} )
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,
                        onClick = {
                            navController.navigate(ScreenRoute.HomeEntry.route){
                                popUpTo(ScreenRoute.HomeEntry.route){
                                    inclusive=true
                                }
                            }
                        }) {
                        Text(text = "Skip")
                    }
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,
                        onClick = {
                            if(rating.doubleValue == 0.0 || review.value.isBlank())
                            {
                                reviewCheck = true
                            }
                            else
                            {
                                viewmodel.thatUser.comments = viewmodel.thatUser.comments.toMutableList().also {
                                    it.add(Comment(viewmodel.thisUser.fullname,viewmodel.thisUser.profilePic,review.value, rating.doubleValue))
                                }
                                viewmodel.reviewUser(navController)
                            }
                        }) {
                        Text(text = "Continue")
                    }

                }

                }
        }
    }

}





var onlineState by mutableStateOf(false)





@Preview(showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    device = "spec:width=1080px,height=2340px,dpi=440"
)
@Composable
fun VocsPreview(){
    JobGigTheme {
//        val state = rememberSaveable {
//            mutableDoubleStateOf(0.0)
//        }
        //RateService()
        // val viewmodel:TutCreate = viewModel()
        // GigAlert(uri = "", name = "Abdul", rating = 4.3, comment ="I like {$} his work" )
        //DonateAsk()
        //DonateB()
        //DonateGive()
        //TutCard(label = "Hello World")
        //DonateWhy(rememberNavController())
        //ManageTuts()
        //Donate()
        //CreateTuts(viewmodel)
//        Notifications(navController = rememberNavController())
//        Box (contentAlignment = Alignment.Center){
//            Dialog(onDismissRequest = { /*TODO*/ }) {
//                //DonateMoney()
//
//            }
//        }

    }
}