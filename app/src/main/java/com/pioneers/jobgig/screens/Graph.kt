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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pioneers.jobgig.viewmodels.OnBoardViewModel

@Composable
fun ScreenNav(navHostController: NavHostController){
    NavHost(navController = navHostController, startDestination = "courseSection"){
        composable(route = ScreenRoute.GetStarted.route){
            GettingStarted(navHostController = navHostController)
        }
        navigation(startDestination = ScreenRoute.Login.route, route = ScreenRoute.Auth.route){

            composable(route = ScreenRoute.Login.route){
                val viemodel = it.authViewModel<OnBoardViewModel>(navController = navHostController)
                LoginScreen(viewModel = viemodel, navController = navHostController)
            }
            composable(route = ScreenRoute.Signup.route){
                val viemodel = it.authViewModel<OnBoardViewModel>(navController = navHostController)
                SignUpScreen(viewModel = viemodel, navController = navHostController)
            }
            composable(route = ScreenRoute.ForgetPassword.route){
                val viemodel = it.authViewModel<OnBoardViewModel>(navController = navHostController)
                ResetPasswordScreen(viewModel = viemodel, navController = navHostController)
            }



        }
        navigation(startDestination = ScreenRoute.HomeScreenCourse.route, route = "courseSection" ){
            composable(route = ScreenRoute.SearchCourse.route){}
            composable(route = ScreenRoute.CourseScreen.route){}
            composable(route = ScreenRoute.AllPopular.route){}
            composable(route = ScreenRoute.SearchCourseResult.route){}
            composable(route = ScreenRoute.EnrolledConfirmed.route){}
            composable(route = ScreenRoute.AllCategory.route){}
            composable(route = ScreenRoute.EnrollPreview.route){}

        }
    }
}


@Composable
inline fun<reified T:ViewModel> NavBackStackEntry.authViewModel(navController: NavController):T {
    val navGraph = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(key1 = this) {
        navController.getBackStackEntry(navGraph)
    }
    return viewModel(parentEntry)
}