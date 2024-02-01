package com.pioneers.jobgig.screens

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextMotion.Companion.Animated
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.viewmodels.CourseViewModel
import com.pioneers.jobgig.viewmodels.OnBoardViewModel

@Composable
fun ScreenNav(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "courseSection"){
        composable(route = ScreenRoute.GetStarted.route){
            GettingStarted(navHostController = navHostController)
        }

        navigation(startDestination = ScreenRoute.Login.route, route = ScreenRoute.Auth.route){

            composable(route = ScreenRoute.Login.route){
                val viemodel = it.sharedViewModel<OnBoardViewModel>(navController = navHostController)
                LoginScreen(viewModel = viemodel, navController = navHostController)
            }
            composable(route = ScreenRoute.Signup.route){
                val viemodel = it.sharedViewModel<OnBoardViewModel>(navController = navHostController)
                SignUpScreen(viewModel = viemodel, navController = navHostController)
            }
            composable(route = ScreenRoute.ForgetPassword.route){
                val viemodel = it.sharedViewModel<OnBoardViewModel>(navController = navHostController)
                ResetPasswordScreen(viewModel = viemodel, navController = navHostController)
            }



        }


        navigation(startDestination = ScreenRoute.HomeScreenCourse.route, route = "courseSection" ){

            composable(route = ScreenRoute.SearchCourse.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen2(viewModel =viemodel , navController = navHostController )
            }
            
            composable(arguments = listOf(
                navArgument("type"){ type = NavType.StringType },navArgument("position"){ type = NavType.IntType }),
                route = ScreenRoute.CourseScreen.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen8(viewModel =viemodel , type =it.arguments?.getString("type")?:"" , position = it.arguments?.getInt("position")?:0)
            }


            composable(route = ScreenRoute.AllPopular.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen5(viewModel =viemodel , navController =navHostController )
            }


            composable(arguments = listOf(navArgument("query"){
                    type = NavType.StringType
                }),
                route = ScreenRoute.SearchCourseResult.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen4(viewModel = viemodel, query =it.arguments?.getString("query")?:"" , navController = navHostController)
            }


            composable(arguments = listOf(navArgument("type"){
                    type = NavType.StringType
                }, navArgument("position"){type = NavType.IntType}),
                route = ScreenRoute.EnrolledConfirmed.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen7(navController =navHostController , viewModel =viemodel , type =it.arguments?.getString("type")?:"" , position =it.arguments?.getInt("position")?:0 )
            }


            composable(route = ScreenRoute.AllCategory.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen3(viewModel =viemodel , navController = navHostController)
            }


            composable(arguments = listOf(navArgument("type"){
                    type = NavType.StringType
                },navArgument("index"){ type = NavType.IntType }),
                route = ScreenRoute.EnrollPreview.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen6(type = it.arguments?.getString("type")?:"", position = it.arguments?.getInt("index")?:0, viewModel =viemodel , navController = navHostController)
            }

            composable(arguments = listOf(
                navArgument("uri"){
                    type = NavType.StringType
                },navArgument("about"){ type = NavType.StringType }),
                route =ScreenRoute.InstructorDetailLScreenRoute.route){
                println(it.arguments?.getString("uri")?.replace("/","%2F")?.replace(",","/"))
                InstructorDetailScreen(uri = it.arguments?.getString("uri")?.replace("/","%2F")?.replace(",","/")?:"", about =it.arguments?.getString("about")?:"" , navController =navHostController )
            }

            composable(route = ScreenRoute.HomeScreenCourse.route){
                val viemodel = it.sharedViewModel<CourseViewModel>(navController = navHostController)
                Screen1(viewModel =viemodel , navController =navHostController )
            }

        }
    }
}


@Composable
inline fun<reified T:ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController):T {
    val navGraph = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraph)
    }
    return viewModel(parentEntry)
}

//https://firebasestorage.googleapis.com/v0/b/psyched-oarlock-405313.appspot.com/o/CourseImage%2Fsalon1.png?alt=media&token=c0df13c8-d854-4f2b-82d9-088f1aef9341