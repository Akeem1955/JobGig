package com.pioneers.jobgig.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    NavHost(navController = navHostController, startDestination = ScreenRoute.GetStarted.route){
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
        navigation(startDestination = ScreenRoute.Home.route, route = ScreenRoute.Main.route ){
            composable(route = ScreenRoute.Home.route){
                Box {
                    Text(text = "Hello ${FirebaseAuth.getInstance().currentUser?.displayName} \n Email:${Firebase.auth.currentUser?.email}", modifier = Modifier.align(Alignment.Center))
                }
            }
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