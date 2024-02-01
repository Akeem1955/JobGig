package com.pioneers.jobgig.screens

import android.content.Intent
import android.widget.RatingBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.ThumbDown
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pioneers.jobgig.R
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.OnBoardViewModel
import com.pioneers.jobgig.viewmodels.ProfileViewmodel
import com.pioneers.jobgig.viewmodels.VocConnectViewModel
import kotlin.math.max

@Composable
fun GigAlert(uri:String,name:String, rating:Double,comment: String){
    val sa = Icons.Rounded.Celebration
    val commentData=comment.split("{$}").mapNotNull {
        val data = it.split("{_}")
       if (data.isNotEmpty() && data.size == 5){
           CommentData(data[0],data[1],data[2],data[3].toDouble(),data[4])
       }else null
    }
    Surface {
        Column {
           Row {
               Icon(imageVector = Icons.Rounded.Celebration, contentDescription = "")
               Text(text = "You have a gig offer from")
           }
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.kniting).data(uri).error(R.drawable.kniting).build(), contentDescription = "")
            Text(text = name)
            Row {
                Text(text = "$rating")
                RatingBar(ratings = rating)
            }
            LazyColumn(){
                items(commentData){item->
                    UserRate(name = item.name, comment = item.comment, uri = item.uri, rating = item.rating, date = item.date)
                }
            }
            Row {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Decline")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Accept")
                }
            }
        }
    }
}
@Composable
fun UserRate(name:String, comment:String, uri:String,rating: Double,date:String){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            AsyncImage(model =ImageRequest.Builder(LocalContext.current).error(R.drawable.kniting).placeholder(R.drawable.kniting).data(uri).build() , contentDescription ="" )
            Text(text = name)
        }
        Row {
            RatingBar(ratings = rating)
            Text(text = date)
        }
        Text(text = comment)
    }
}
data class CommentData(val name:String, val comment: String, val uri: String ,val rating: Double , val date: String)



@Composable
fun VocBox(viewModel: VocConnectViewModel, navController: NavController){
    LaunchedEffect(key1 = viewModel.transactSession){
        if (viewModel.transactSession.payed){
            navController.navigate(route = ScreenRoute.ServiceRate.route){
                popUpTo(ScreenRoute.ServiceSession.route){
                    inclusive = true
                }
            }
        }
    }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp) ,modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth())  {
            if (viewModel.transactSession.completed){
                Text(text = "Waiting For Client to Pay",fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = { navController.navigate(route = ScreenRoute.ServiceChat.route) }) {
                Icon(imageVector = Icons.Rounded.Chat, contentDescription = "")
            }
        }
        Button(onClick = {viewModel.transactSession.completed = true; viewModel.updateSession()}) {
                Text(text = "Work Completed", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
        }

    }

}





@Composable
fun ClientBox(navController: NavController, viewModel: VocConnectViewModel){
    LaunchedEffect(key1 = viewModel.transactSession){
        if (viewModel.transactSession.payed){
            navController.navigate(route = ScreenRoute.ServiceRate.route){
                popUpTo(ScreenRoute.ServiceSession.route){
                    inclusive = true
                }
            }
        }
    }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp) ,modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth())  {
           if (viewModel.transactSession.completed){
               Text(text = "Worker Finished", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
           }else{
               Text(text = "Waiting for worker to finish", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
           }
            IconButton(onClick = {navController.navigate(route = ScreenRoute.ServiceChat.route)  }) {
                Icon(imageVector = Icons.Rounded.Chat, contentDescription = "")
            }
        }
        Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
            id = R.color.btn
        ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { viewModel.payWorker()}) {
            Text(text = "Pay Worker")
        }

    }
}





@Composable
fun AgreeBox(navController: NavController, viewmodel: VocConnectViewModel, state: MutableState<TransactionStage>){
    LaunchedEffect(key1 = viewmodel.transactSession){
        if(viewmodel.transactSession.agreement[ClientType.Client.name] == Agreement.Cancel.name
            || viewmodel.transactSession.agreement[ClientType.Worker.name] == Agreement.Cancel.name){
            navController.navigate(route = ScreenRoute.ServiceRate.route){
                popUpTo(ScreenRoute.ServiceSession.route){
                    inclusive = true
                }
            }
        }
        if(viewmodel.transactSession.agreement[ClientType.Client.name] == Agreement.Agree.name
            && viewmodel.transactSession.agreement[ClientType.Worker.name] == Agreement.Agree.name){
            state.value = TransactionStage.AgreedStage
        }

    }
    val agreeIcon = when(viewmodel.transactSession.agreement[viewmodel.clientType.name]){
        Agreement.Agree.name ->{Icons.Rounded.ThumbUp}
        else ->{Icons.Rounded.ThumbDown}
    }
    if(viewmodel.transactSession.agreement[viewmodel.clientType.name] == Agreement.Agree.name) {
        Dialog(onDismissRequest = { /*TODO*/ }) {
            Surface(modifier = Modifier.widthIn(max = 300.dp),shape = MaterialTheme.shapes.medium, tonalElevation = 24.dp) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp) ,modifier = Modifier.fillMaxWidth()) {
                    Text(text = "${viewmodel.clientType.name} As Agreed To Your Price And Waiting For You",
                        fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        fontWeight = FontWeight.Bold)
                    Row {
                        Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                            id = R.color.btn
                        ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { }) {
                            Text(text = "Continue")
                        }
                    }
                }
            }
        }
    }
    var myPriceDialog by rememberSaveable {
        mutableStateOf(false)
    }
    if (myPriceDialog){
        Dialog(onDismissRequest = { myPriceDialog = false}) {
            val textState = rememberSaveable {
                mutableStateOf("${viewmodel.transactSession.prices[viewmodel.yourType.value.name]}")
            }
            Surface (shape = MaterialTheme.shapes.large, tonalElevation = 24.dp){
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp),verticalAlignment = Alignment.CenterVertically,modifier = Modifier.widthIn(max = 350.dp)) {
                    TextField(modifier = Modifier.weight(1f) ,leadingIcon = { Icon(painter = painterResource(id = R.drawable.tabler_currency_naira),
                        contentDescription ="" )} ,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        value = textState.value, onValueChange = {update->textState.value= update})
                    Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id = R.color.btn
                    ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { viewmodel.transactSession.prices[viewmodel.yourType.value.name] = textState.value.toDouble(); myPriceDialog =false}) {
                        Text(text = " Set Price", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }


    Column(verticalArrangement = Arrangement.spacedBy(16.dp) ,modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth())  {
            Row {
                Button(colors = ButtonDefaults.buttonColors(containerColor = colorResource(
                    id = R.color.btn
                ), contentColor = Color.White),shape = MaterialTheme.shapes.large,onClick = { }) {
                    Text(text = "Your Price ${viewmodel.transactSession.prices[viewmodel.yourType.value.name]}", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
                }
                Icon(imageVector = agreeIcon, contentDescription = "", tint = colorResource(id = R.color.btn))
            }
            IconButton(onClick = { navController.navigate(route = ScreenRoute.ServiceChat.route) }) {
                Icon(imageVector = Icons.Rounded.Chat, contentDescription = "")
            }
        }

        Text(text = "Client Price : ${viewmodel.transactSession.prices[viewmodel.clientType.name]}",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.SpaceBetween , modifier = Modifier.fillMaxWidth())  {
            Button(colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error, contentColor = Color.White),
                shape = MaterialTheme.shapes.large,
                onClick = {
                    viewmodel.transactSession.agreement[viewmodel.yourType.value.name] = Agreement.Cancel.name
                    viewmodel.updateSession()}) {
                Text(text = "Cancel", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
            }
            Button(onClick = { viewmodel.transactSession.agreement[viewmodel.yourType.value.name] = Agreement.Agree.name;viewmodel.updateSession() }) {
                Text(text = "Agree", fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
            }
        }

    }
}





@Composable
fun OldRatingBar(state:MutableDoubleState){
    AndroidView(factory = {ctx-> RatingBar(ctx).also { it.setOnRatingBarChangeListener { _, rating, fromUser -> 
        if (fromUser){
            state.doubleValue = rating.toDouble()
        }
    } }})
}



@Composable
fun Simulator(){
    val ctx = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp),verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(onClick = {
            val intent = Intent(ctx,com.pioneers.jobgig.services.JobSearch::class.java)
            ctx.startService(intent)
        }) {
            Text(text = "Start")
        }
        Button(onClick = {
            val intent = Intent(ctx,com.pioneers.jobgig.services.JobSearch::class.java)
            intent.action = "simulate_job"
            ctx.startService(intent)
        }) {
            Text(text = "Send Job")
        }
    }
}

enum class Agreement{
    Init,
    Cancel,
    Agree
}



@Composable
fun ProfileSetting(viewmodel: ProfileViewmodel){
    var type by rememberSaveable {
        mutableIntStateOf(0)
    }
    var label by rememberSaveable {
        mutableStateOf("")
    }
    val openState = rememberSaveable {
        mutableStateOf(false)
    }
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
    if(openState.value){
        ProfileEdit(openState =openState , label = label, viewmodel = viewmodel, type =type,ctx)
    }
    val photoPicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(), onResult = {ur ->
        viewmodel.profileUri = ur.toString()
        viewmodel.saveProfile(ctx)
    } )
   Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)){
       Surface( color = MaterialTheme.colorScheme.surfaceVariant){
           Row (verticalAlignment = Alignment.CenterVertically,modifier = Modifier
               .fillMaxWidth()
               .statusBarsPadding()){
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
               }
               Text(text = "Profile", fontSize = MaterialTheme.typography.titleLarge.fontSize)
           }
       }
       Surface(modifier = Modifier
           .weight(1f)
           .fillMaxWidth()
           .padding(top = 16.dp)) {
           Column(modifier = Modifier
               .fillMaxSize()
               .padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
               Box(modifier = Modifier){
                   AsyncImage(modifier = Modifier
                       .size(150.dp)
                       .clip(CircleShape) ,contentScale = ContentScale.Crop,model = ImageRequest.Builder(LocalContext.current).data(OnBoardViewModel.currentUser.profilePic).error(R.drawable.round_image_24).build(), contentDescription ="" )
                   IconButton(modifier = Modifier
                       .align(Alignment.BottomEnd)
                       .clip(CircleShape)
                       .background(colorResource(id = R.color.btn)),
                       onClick = {
                           photoPicker.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                       }) {
                       Icon(imageVector = Icons.Rounded.AddAPhoto, contentDescription = "")
                   }
               }
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(16.dp),
                   modifier = Modifier.fillMaxWidth().clickable {label ="Name";type=0;openState.value=true}) {
                   Icon(imageVector = Icons.Rounded.Person, contentDescription = "")
                   Column(modifier = Modifier.weight(1f,true)) {
                   Text(text = "Name", fontSize = MaterialTheme.typography.labelSmall.fontSize)
                   Text(text = viewmodel.name, fontSize = MaterialTheme.typography.labelMedium.fontSize)
               }
               }
               Divider()
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(16.dp),
                   modifier = Modifier.fillMaxWidth().clickable {label ="About";type=1;openState.value=true}) {
                   Icon(imageVector = Icons.Outlined.Info, contentDescription ="" )
                   Column(modifier = Modifier.weight(1f,true)) {
                       Text(text = "About", fontSize = MaterialTheme.typography.labelSmall.fontSize)
                       Text(text = viewmodel.name, fontSize = MaterialTheme.typography.labelMedium.fontSize)
                   }

               }
               Divider()
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(16.dp),
                   modifier = Modifier.fillMaxWidth()) {
                   Icon(imageVector =Icons.Rounded.Phone , contentDescription ="" )
                   Column(modifier = Modifier.weight(1f,true)) {
                       Text(text = "Phone", fontSize = MaterialTheme.typography.labelSmall.fontSize)
                       Text(text = OnBoardViewModel.currentUser.phone, fontSize = MaterialTheme.typography.labelMedium.fontSize)
                   }
               }
               Divider()
               Row(verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.spacedBy(16.dp),
                   modifier = Modifier.fillMaxWidth().clickable {label ="Address";type=2;openState.value=true}) {
                   Icon(imageVector =Icons.Rounded.Phone , contentDescription ="" )
                   Column(modifier = Modifier.weight(1f,true)) {
                       Text(text = "Address", fontSize = MaterialTheme.typography.labelSmall.fontSize)
                       Text(text = viewmodel.address, fontSize = MaterialTheme.typography.labelMedium.fontSize)
                   }
               }

           }
       }
   }
}


@Preview( showSystemUi = true, apiLevel = 34, backgroundColor = 0xFF463D3D)
@Composable
fun VocPreview() {
    JobGigTheme {
       // val viewmodel:VocConnectViewModel = viewModel()
        //AvailableWorker()
        //VocConversation()
        //ConversationHeader()
        //EmployeeDetail()
        //SearchRes(query = "Salon")
        //Simulator()
       // ProfileSetting()
        //ServiceSearch(viewmodel, rememberNavController())
        //AboutMe(aboutMe = "I Love My Religion And I Want to enter Paradise So I Love Allah", title = "AboutMe")
    }
}


