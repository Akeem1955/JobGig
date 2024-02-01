package com.pioneers.jobgig.screens

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.pioneers.jobgig.R
import com.pioneers.jobgig.dataobj.utils.Category
import com.pioneers.jobgig.dataobj.utils.CourseData
import com.pioneers.jobgig.sealed.CourseCategoryIcon
import com.pioneers.jobgig.sealed.CourseContentDesign
import com.pioneers.jobgig.sealed.CourseInfoDesign
import com.pioneers.jobgig.services.preference.AppPreference
import com.pioneers.jobgig.services.preference.datastore
import com.pioneers.jobgig.ui.theme.JobGigTheme
import com.pioneers.jobgig.viewmodels.CourseViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun InstructorDetailScreen(uri: String, about:String,navController: NavController ){
    println(uri)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Surface(color = MaterialTheme.colorScheme.background,modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()) {
            Column(verticalArrangement = Arrangement.spacedBy(32.dp),horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                .padding(top = 64.dp, start = 8.dp, end = 16.dp)
                .verticalScroll(state = rememberScrollState())
                .navigationBarsPadding()) {
                AsyncImage(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .clip(MaterialTheme.shapes.small)
                    ,contentScale = ContentScale.Crop ,model =ImageRequest.Builder(LocalContext.current).data(Uri.parse(uri)).error(R.drawable.round_image_24).build() , contentDescription = "")
                Divider()
                Text(text = "About", fontWeight = FontWeight.Bold)
                Text(text =about)
            }
        }
        IconButton(modifier = Modifier.statusBarsPadding() ,onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
        }
    }
}
@Composable
fun Screen1(viewModel: CourseViewModel, navController: NavController){
    val snackbarHostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState)},containerColor =  colorResource(id = R.color.btn)) {
        if (viewModel.loadingState){
            Dialog(onDismissRequest = {}) {
                CircularProgressIndicator()
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Header(modifier = Modifier.padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp),
                uri =Uri.parse("https://images.unsplash.com/photo-1695653420644-ab3d6a039d53?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHwxfHx8ZW58MHx8fHx8"),
                title = "Find your favorite course")
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.weight(1f)) {
                Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = it.calculateBottomPadding())) {
                    Column(verticalArrangement = Arrangement.spacedBy(32.dp),modifier = Modifier
                        .padding(start = 16.dp, bottom = 16.dp)
                        .verticalScroll(state = rememberScrollState())) {
                        SearchCourseBtn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),navController)
                        TopCategory(modifier = Modifier, items = viewModel.topCategoryCourse.subList(0,viewModel.topCategoryCourse.size/2), navController)
                        Popular(modifier = Modifier, items  = viewModel.popularCourse, navController)
                    }
                }

            }
        }
    }
}
@Composable
fun Screen2(viewModel: CourseViewModel, navController: NavController){
    Surface(color = colorResource(id = R.color.btn)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = MaterialTheme.colorScheme.background)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .background(color = MaterialTheme.colorScheme.background)) {
                Search(navController, viewModel)
            }
        }
    }
}
@Composable
fun Screen3(viewModel: CourseViewModel, navController: NavController){
    val allCategory = viewModel.topCategoryCourse
    //all category screen
    Surface(color = colorResource(id = R.color.btn3)) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderMin(modifier = Modifier
                .padding(bottom = 8.dp).statusBarsPadding(), uri = null, title ="All Category",navController)
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                LazyVerticalGrid(horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.navigationBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    columns = GridCells.Adaptive(100.dp), state = LazyGridState(),
                    contentPadding = PaddingValues(8.dp)){
                    items(allCategory){item ->
                        val helper =CourseCategoryIcon(item.icon)
                        TopCategoryItem(category = item.icon.replace("_"," "), iconRes = helper.iconRes, color = item.color, navController = navController,helper.keyword)
                    }
                }
            }
        }

    }
}
@Composable
fun Screen4(viewModel: CourseViewModel, query:String,navController: NavController){
    //search result screen
    val listState=rememberLazyListState()
    val searchresults by viewModel.searchResultCourse
    LaunchedEffect(key1 = true){

        viewModel.getSearchItem(query)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        if (viewModel.loadingState){
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator()
            }
        }

        Surface(modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
                .navigationBarsPadding()) {
                SearchCourseBtn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),navController)
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),state = listState, modifier = Modifier.weight(1f,true)){
                    itemsIndexed(searchresults){pos, item ->
                        if (item != null) {
                            CourseCardSearch(data = item, navController =navController,pos,"search")
                        }
                    }

                }

            }

        }
    }
}
@Composable
fun Screen5(viewModel: CourseViewModel,navController: NavController){
    //all popular course
    val listState=rememberLazyListState()
    val popularCourse by viewModel.popularCourse
    LaunchedEffect(key1 = listState.canScrollForward){
        if(!listState.canScrollForward){
            viewModel.loadMorePopularQuery()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        if (viewModel.loadingState){
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator()
            }
        }

        Surface(modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()) {
                SearchCourseBtn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),navController)
                LazyColumn(contentPadding = PaddingValues(top = 8.dp),verticalArrangement = Arrangement.spacedBy(16.dp),state = listState, modifier = Modifier.weight(1f,true)){
                    itemsIndexed(popularCourse){ pos,item ->
                        if (item != null) {
                            CourseCardSearch(data = item, navController =navController,pos,"popular" )
                        }
                    }

                }

            }

        }
    }
}
@Composable
fun Screen6(type:String, position:Int,viewModel: CourseViewModel, navController: NavController){
    //enroll preview
    val coursedata = if(type == "search") viewModel.searchResultCourse.value[position] else viewModel.popularCourse.value[position]
    Box(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).error(R.drawable.round_image_24).data(coursedata?.imageUri).build(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .align(alignment = Alignment.TopCenter)
                .fillMaxHeight(0.4f))
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)) {
            Column(modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
            ) {
                LazyColumn(state = rememberLazyListState(), modifier= Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .weight(1f, true)){
                    item {
                        CourseIntroRate(
                            title = coursedata?.title ?:"null",
                            duration = coursedata?.duration ?:"null",
                            ratings = coursedata?.rating ?:0.0,
                            studentEnrolled = coursedata?.learners ?:0,
                            numRating = coursedata?.numOfRate ?:0
                        )
                    }
                    item {
                        CourseInfo(title = "What You will Learn", content = CourseContentDesign.ListDesign(coursedata?.whatLearn ?: emptyList()), infoType = CourseInfoDesign.CheckDesign, contentType = CourseContentDesign.Items, navController = navController )
                    }
                    item {
                        CourseInfo(title = "Requirements", content = CourseContentDesign.ListDesign(coursedata?.requirement ?: emptyList()), infoType = CourseInfoDesign.BulletDesign, contentType = CourseContentDesign.Items,navController = navController  )
                    }
                    item {
                        CourseInfo(title = "Description", content = CourseContentDesign.TextDesign(coursedata?.description ?: ""), infoType = CourseInfoDesign.CheckDesign, contentType = CourseContentDesign.Single,navController = navController )

                    }
                    item {
                        CourseInfo(title = "Instructor", content = CourseContentDesign.InstructorDesign(coursedata?.instructorInfo ?: emptyList()), infoType = CourseInfoDesign.InstructorDesign, contentType = CourseContentDesign.Instructor,navController = navController  )
                    }
                }
                Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background) {
                    Row(modifier = Modifier.padding(4.dp),verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Free", modifier = Modifier
                            .weight(1f, true)
                            .background(color = MaterialTheme.colorScheme.background), textAlign = TextAlign.Center, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.ExtraBold)
                        Text(color = Color.White,text = "Enroll Now", modifier = Modifier
                            .background(color = Color.Magenta)
                            .clickable {
                                navController.navigate(
                                    route = ScreenRoute.EnrolledConfirmed.enrollIndex(
                                        position,
                                        type
                                    )
                                ) {
                                    popUpTo(ScreenRoute.EnrollPreview.route) {
                                        inclusive = true
                                    }
                                }
                            }
                            .weight(2f, true)
                            .padding(12.dp), textAlign = TextAlign.Center)
                    }
                }
            }
        }
        IconButton(onClick = {navController.popBackStack()}, modifier = Modifier.statusBarsPadding()) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "")
        }
    }
}
@Composable
fun Screen7(navController: NavController, viewModel: CourseViewModel, type: String, position: Int){
    //you are enrolled
    val coursedata = if(type == "search") viewModel.searchResultCourse.value[position] else viewModel.popularCourse.value[position]
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
            .padding(12.dp)
            .statusBarsPadding()) {
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
            }
            Row(modifier = Modifier.heightIn(max = 120.dp),horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                AsyncImage(modifier = Modifier.weight(1f,true),model = ImageRequest.Builder(LocalContext.current).data(coursedata?.imageUri).build(), contentDescription = "")

                Column(modifier = Modifier.weight(2f,true) ,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "You are now enrolled in:")
                    Text(text ="${coursedata?.title}" )
                    Text(text = "${
                        coursedata?.instructorInfo?.joinToString(",") {
                            it.name
                        }
                    }")
                }
            }

            Button(colors = ButtonDefaults.buttonColors(contentColor = Color.White,containerColor = colorResource(id = R.color.btn)),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(route = ScreenRoute.CourseScreen.courseArgs(type, position)) {
                    popUpTo(ScreenRoute.EnrolledConfirmed.route){
                        inclusive =true
                    }
                } }) {
                Text(text = "Get Started")
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Screen8(viewModel: CourseViewModel, type: String, position: Int){
    //course screen
    val ctx = LocalContext.current
    val contents = if(type == "search") viewModel.searchResultCourse.value[position] else viewModel.popularCourse.value[position]
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
    LaunchedEffect(key1 = true){
        viewModel.init(ctx)
        viewModel.addMediaItems(contents?.content?: emptyList())
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = colorResource(id = R.color.btn))) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(color = Color.Black)
            .height(250.dp)) {
            VideoPlayerScreen(viewModel = viewModel)
        }
        Column(modifier = Modifier
            .weight(1f)
            .navigationBarsPadding()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItem.forEachIndexed { index, s ->
                    Tab(selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text ={ Text(text = s)}
                    )
                }
            }
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f,true)) {index ->
                Surface(color = MaterialTheme.colorScheme.background ,modifier = Modifier.fillMaxSize()) {
                    when(index){
                        0->{
                            CourseVideoItems(viewModel = viewModel, contents = contents?.content ?: emptyList())
                        }
                        1->{
                            Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
                                Text(text = "Coming Soon....")
                            }}
                    }
                }
            }
        }



    }
}
@Composable
fun SearchCourseBtn(modifier: Modifier, navController: NavController){
    Surface(tonalElevation = 10.dp,border = BorderStroke(4.dp,MaterialTheme.colorScheme.surfaceVariant),color = MaterialTheme.colorScheme.surfaceVariant ,shape = MaterialTheme.shapes.extraLarge,modifier = modifier.clickable { navController.navigate(route = ScreenRoute.SearchCourse.route) }) {
        Row (horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .padding(end = 64.dp, top = 8.dp, bottom = 8.dp)){
            Icon(imageVector = Icons.Rounded.Search, contentDescription = "")
            Text(text = "Search for courses", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        }
    }
}
@Composable
fun TopCategoryItem(category:String, iconRes:Int,color:String, navController: NavController,query: String ){
    println("$color nah you Cause am")
    Surface(color = Color(android.graphics.Color.parseColor(color)),shape = MaterialTheme.shapes.medium, modifier = Modifier
        .width(120.dp)
        .clickable { navController.navigate(route = ScreenRoute.SearchCourseResult.query(query)) }
        .height(158.dp)) {
        Column(verticalArrangement = Arrangement.SpaceEvenly,horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .padding(top = 16.dp)) {
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).placeholder(iconRes).data(iconRes).build(),
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
@Composable
fun TopCategory(modifier: Modifier, items:List<Category>, navController: NavController){
    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Top Category", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            TextButton(onClick = { navController.navigate(route = ScreenRoute.AllCategory.route) }) {
                Text(text = "see all")
            }
        }
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp) ,
            state = rememberLazyListState(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)){
            items(items){item->
                val helper =CourseCategoryIcon(item.icon)
                println(item.color)
                TopCategoryItem(category = item.icon.replace("_"," "), iconRes = helper.iconRes, color = item.color, navController, helper.keyword)
            }
        }
    }
}
@Composable
fun Popular(modifier: Modifier,items:State<List<CourseData?>>,navController: NavController){
    Column(modifier = modifier,verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Popular", fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            TextButton(onClick = { navController.navigate(route = ScreenRoute.AllPopular.route) }) {
                Text(text = "see all")
            }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)){
            items(items.value.subList(0,items.value.size/2)){item->
                if (item != null) {
                    PopularCourseCard(item)
                }
            }
        }
    }
}
@Composable
fun PopularCourseCard(data:CourseData){
    Column(verticalArrangement = Arrangement.spacedBy(4.dp),modifier = Modifier.width(200.dp)) {
        Surface(color = MaterialTheme.colorScheme.surfaceVariant ,shape = MaterialTheme.shapes.small, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)) {
            AsyncImage(contentScale = ContentScale.Crop ,model = ImageRequest.Builder(LocalContext.current).data(data.imageUri).error(R.drawable.round_image_24).build(), contentDescription = "")
        }
        Text(text = data.title, modifier = Modifier.heightIn(max = 50.dp), overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
        Row(verticalAlignment = Alignment.CenterVertically){
            Text(text = "${data.rating}")
            Icon(imageVector = Icons.Rounded.StarRate, contentDescription = "", tint = colorResource(
                id = R.color.btn2
            ))
            Text(text = "(${String.format("%,d",data.learners)})", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = MaterialTheme.typography.labelSmall.fontSize)
        }
    }
}
@Composable
fun Header(modifier: Modifier, uri: Uri?,title:String){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween, modifier= Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = Color.White)
            }
            AsyncImage(contentScale = ContentScale.Crop ,model = ImageRequest.Builder(LocalContext.current).decoderFactory(SvgDecoder.Factory()).placeholder(R.drawable.round_account_circle_24).data(uri).build(), contentDescription = "",modifier = Modifier
                .size(60.dp)
                .clip(CircleShape))

        }
        Text(color = Color.White,text = title, fontSize = MaterialTheme.typography.headlineMedium.fontSize, lineHeight = MaterialTheme.typography.headlineMedium.lineHeight, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
    }
}
@Composable
fun HeaderMin(modifier: Modifier, uri: Uri?,title:String, navController: NavController){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween, modifier= Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp)) {
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "", tint = Color.White)
            }
            AsyncImage(contentScale = ContentScale.Crop ,model = ImageRequest.Builder(LocalContext.current).decoderFactory(SvgDecoder.Factory()).error(R.drawable.jewelry).data(uri).build(), contentDescription = "",modifier = Modifier
                .size(50.dp)
                .clip(CircleShape))

        }
        Text(color = Color.White,text = title, fontSize = MaterialTheme.typography.bodyLarge.fontSize, lineHeight = MaterialTheme.typography.headlineMedium.lineHeight, fontWeight = FontWeight.Bold)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(navController: NavController,viewModel: CourseViewModel){
    val ctx = LocalContext.current
    var query by rememberSaveable {
        mutableStateOf("")
    }
    val recentSearch by ctx.datastore.data.collectAsStateWithLifecycle(initialValue = AppPreference())
    val icon = Icons.Rounded.ArrowBack

    SearchBar(placeholder = { Text(text = "Search for courses")},
        leadingIcon = { Icon(imageVector = icon, contentDescription = "")},
        query = query,
        onQueryChange = {update->query = update},
        onSearch = {viewModel.updateRecentSearches(ctx.datastore,query);navController.navigate(route = ScreenRoute.SearchCourseResult.query(query)){
            popUpTo(ScreenRoute.SearchCourse.route){inclusive = true}
        } },
        active = true, onActiveChange = {update->
            if(!update){
                navController.popBackStack()
            }}) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),contentPadding = PaddingValues(16.dp), state = rememberLazyListState()){
            items(recentSearch.searches){item->
                RecentSearch(navController = navController, keyword = item)
            }
        }
    }
}
@Composable
fun CourseCardSearch(data:CourseData, navController: NavController, pos:Int, type: String){
    Surface(modifier = Modifier
        .clickable { navController.navigate(ScreenRoute.EnrollPreview.enrollIndex(pos, type)) },color = MaterialTheme.colorScheme.background) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .height(100.dp)) {
            AsyncImage(contentScale = ContentScale.FillBounds,
                model = ImageRequest.Builder(LocalContext.current).data(data.imageUri) .error(R.drawable.round_image_24).build(),
                contentDescription = "", modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .height(100.dp)
                    .weight(1f))
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(2f),verticalArrangement = Arrangement.SpaceAround) {
                Text(text = data.title, modifier = Modifier.heightIn(max = 50.dp), overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = "${data.rating}")
                    Icon(imageVector = Icons.Rounded.StarRate, contentDescription = "", tint = colorResource(
                        id = R.color.btn2
                    ))
                    Text(text = "(${String.format("%,d",data.learners)})", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = MaterialTheme.typography.labelSmall.fontSize)
                }
            }
        }
    }
}
@Composable
fun RecentSearch(navController: NavController?, keyword:String){
    Row(horizontalArrangement = Arrangement.spacedBy(32.dp) ,verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .clickable { navController?.navigate(route = ScreenRoute.SearchCourseResult.query(keyword)) }) {
        Icon(imageVector = Icons.Rounded.History, contentDescription ="", tint = MaterialTheme.colorScheme.onBackground )
        Text(text = keyword, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold, fontSize = MaterialTheme.typography.bodyLarge.fontSize)
    }
}





@Composable
fun Test(){
    val listState =  rememberLazyListState()
    val mutableflow = remember {
        MutableStateFlow(mutableStateListOf("apple","banana"))
    }
    val state by mutableflow.collectAsStateWithLifecycle()

    var immutableFruit by remember {
        mutableStateOf(listOf("StrawBerry","Mango","Berries"))
    }
    LaunchedEffect(key1 =listState){
        if(!listState.canScrollForward){
            println("No More items")
            mutableflow.value.add("PawPaw")
        }
    }
    Column (modifier = Modifier.fillMaxSize()){
        Surface(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()) {
            LazyColumn(modifier = Modifier.background(color = Color.White),state = listState,horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.spacedBy(16.dp),contentPadding = PaddingValues(16.dp)){
                items(state){item->
                    Text(text = item, color = Color.Magenta)
                }
            }
        }
        Button(onClick = { mutableflow.value.add("PawPaw");mutableflow.value.add("Orange");immutableFruit = listOf("Orange","PineApple","Guava") }) {
            Text(text = "Change Fruit Types")
        }
    }
}
@Composable
fun Anim(){
    var state by remember {
        mutableStateOf(false)
    }
    Column {
        Surface(modifier = Modifier.weight(1f,true)) {
            AnimatedContent(targetState = state, label = "") {
                when(it){
                    true ->{
                        //Screen1()
                    }
                    false ->{
                        //Screen2()
                    }
                }
            }
        }
        Button(onClick = { state = !state }) {
            Text(text = "Animate Change")
        }
    }
}















@Composable
fun VideoPlayerScreen(viewModel: CourseViewModel){
    val owner = LocalLifecycleOwner.current
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    DisposableEffect(key1 = owner){
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
            when(event){
                Lifecycle.Event.ON_STOP -> {
                    println("This is on stop")
                    viewModel.playeR?.pause()

                }
                Lifecycle.Event.ON_RESUME -> {
                    println("This is on Resume")

                }
                Lifecycle.Event.ON_PAUSE -> {
                    viewModel.playeR?.pause()
                    println("This is on Pause")
                }
                else -> Unit
            }

        }
        owner.lifecycle.addObserver(observer)
        onDispose { owner.lifecycle.removeObserver(observer) }
    }

    Surface(shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)) {
        if (viewModel.playeR != null){
            VideoPlayer(modifier = Modifier
                .fillMaxSize(), player = viewModel.playeR!!, lifecycle = lifecycle)
        }
    }
}