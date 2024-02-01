package com.pioneers.jobgig.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Handyman
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SpaceDashboard
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.decode.ImageSource
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.R
import com.pioneers.jobgig.sealed.HomeCardViews
import com.pioneers.jobgig.services.preference.datastore
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.OnBoardViewModel


@Composable
fun HomeCardView(type:HomeCardViews, navController: NavController, route:String){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        tonalElevation = 24.dp,
        shape = MaterialTheme.shapes.medium) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp),verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(type.icon).build(),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentDescription ="" )
                Column(modifier = Modifier.weight(1f)){
                    Text(text = type.title, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.Bold)
                    Text(text = type.label, fontSize = MaterialTheme.typography.labelSmall.fontSize, lineHeight = MaterialTheme.typography.labelSmall.lineHeight)
                }
                IconButton(onClick = { navController.navigate(route = route) }) {
                    Icon(tint= MaterialTheme.colorScheme.surfaceTint,imageVector = Icons.Outlined.ArrowForwardIos, contentDescription ="")
                }

            }
        }
    }
}



@Composable
fun HomeScreen(navController: NavController){
    val currentUser by remember {
        mutableStateOf(Firebase.auth.currentUser)
    }
    val headColor =colorResource(id = R.color.btn)

    Scaffold(containerColor = headColor, bottomBar = { BottomItem(navController)}) {
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
                        .error(R.drawable.kniting)
                        .placeholder(R.drawable.kniting)
                        .build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentDescription ="" )
                    Text(text = "Hi, ${currentUser?.displayName?:"Abdul"}", color = Color.White, fontSize = MaterialTheme.typography.bodySmall.fontSize, fontWeight = FontWeight.Bold)
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
                LazyColumn(modifier = Modifier.padding(top = 16.dp, bottom = 100.dp), verticalArrangement = Arrangement.spacedBy(16.dp),horizontalAlignment = Alignment.CenterHorizontally){
                    item { BalanceDash() }
                    item { HomeCardView(type = HomeCardViews.GetAGig, navController =navController, ScreenRoute.VocRequest.route ) }
                    item { HomeCardView(type = HomeCardViews.Service, navController =navController, ScreenRoute.ServiceSearch.route  ) }
                    item { HomeCardView(type = HomeCardViews.Courses, navController = navController, ScreenRoute.HomeScreenCourse.route ) }
                }
            }
        }

    }
}


@Composable
fun BottomItem(navController: NavController){
    var route:ScreenRoute by rememberSaveable {
        mutableStateOf(ScreenRoute.Main)
    }
   NavigationBar {
       NavigationBarItem(selected = route.route == ScreenRoute.Main.route,
           onClick = { route = ScreenRoute.Main; navController.navigate(ScreenRoute.Main.route)},
           label = { Text(text = "Home") },
           icon = {
               Icon(
                   imageVector = Icons.Rounded.Home,
                   contentDescription = ""
               )
           })
      if (OnBoardViewModel.currentUser.verified){
          NavigationBarItem(selected = route.route == ScreenRoute.VocDashBoard.route,
              onClick = { route = ScreenRoute.VocDashBoard;navController.navigate(ScreenRoute.VocDashBoard.route)},
              label = { Text(text = "Dashboard") },
              icon = {
                  Icon(
                      imageVector = Icons.Rounded.SpaceDashboard,
                      contentDescription = ""
                  )
              })
      }
       NavigationBarItem(selected = route.route == ScreenRoute.Donate.route,
           onClick = { route = ScreenRoute.Donate;navController.navigate(ScreenRoute.Donate.route)},
           label = { Text(text = "Support") },
           icon = {
               Icon(
                   imageVector = Icons.Rounded.CardGiftcard,
                   contentDescription = ""
               )
           })
   }

}


@Preview
@Composable
fun HomePreview(){
    JobGigTheme {
        //HomeScreen()
    }
}


val NavController.canGoBack:Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED


val NavHostController.canGoBack:Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED