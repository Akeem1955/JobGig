package com.pioneers.jobgig.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.R
import com.pioneers.jobgig.sealed.HomeCardViews
import com.pioneers.jobgig.services.preference.datastore
import com.pioneers.jobgig.ui.theme.JobGigTheme





@Composable
fun HomeCardView(type:HomeCardViews, navController: NavController?){
    Surface(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(200.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(type.icon).build(),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentDescription ="" )
                Spacer(modifier = Modifier
                    .weight(1f)
                    .height(80.dp))
                IconButton(onClick = {  }) {
                    Icon(tint= MaterialTheme.colorScheme.surfaceTint,imageVector = Icons.Outlined.ArrowForwardIos, contentDescription ="")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier.padding(start = 16.dp),text = type.title, fontSize = MaterialTheme.typography.titleLarge .fontSize, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(modifier = Modifier.padding(start = 16.dp),text = type.label, fontSize = MaterialTheme.typography.bodySmall.fontSize)
        }
    }
}



@Composable
fun HomeScreen(){

    val currentUser by remember {
        mutableStateOf(Firebase.auth.currentUser)
    }
    Scaffold(containerColor = colorResource(id = R.color.btn)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp, bottom = 8.dp)
                .fillMaxWidth())
            {
                Column {
                    AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                        .decoderFactory(SvgDecoder.Factory())
                        .placeholder(R.drawable.round_account_circle_24)
                        .crossfade(500)
                        .data(currentUser?.photoUrl)
                        .build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentDescription ="" )
                    Text(text = "Hi, ${currentUser?.displayName}")
                }
                Box(modifier = Modifier.size(30.dp)) {
                    IconButton(onClick = {  }) {
                        Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "")
                    }
                    Surface(color = MaterialTheme.colorScheme.error,modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(10.dp)
                        .clip(CircleShape)){
                        Text(text = "9", textAlign = TextAlign.Center, fontSize = 6.sp )
                    }
                }
            }
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
                .weight(1f, true)
                .fillMaxWidth()) {
                LazyColumn(){
                    item { HomeCardView(type = HomeCardViews.GetAGig, navController =null ) }
                    item { HomeCardView(type = HomeCardViews.Service, navController =null ) }
                    item { HomeCardView(type = HomeCardViews.Courses, navController = null) }
                }
            }
        }

    }
}



