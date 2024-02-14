package com.pioneers.jobgig.screens

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.pioneers.jobgig.viewmodels.CourseViewModel
import com.pioneers.jobgig.viewmodels.DashboardViewmodel
import com.pioneers.jobgig.viewmodels.DonateViewModel
import com.pioneers.jobgig.viewmodels.OnBoardViewModel
import com.pioneers.jobgig.viewmodels.ProfileViewmodel
import com.pioneers.jobgig.viewmodels.TutCreate
import com.pioneers.jobgig.viewmodels.VocConnectViewModel
import com.pioneers.jobgig.viewmodels.VocViewmodel

@Composable
fun ScreenNav(navHostController: NavHostController,start:String){
    NavHost(navController = navHostController, startDestination = start){
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
        navigation(ScreenRoute.ServiceSearch.route,"Service"){
            composable(route = ScreenRoute.ServiceSearch.route){
                val viewmodel = it.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                ServiceSearch(viewmodel =viewmodel , navController = navHostController)
            }
            composable(route = ScreenRoute.ServiceChat.route){
                val viewmodel = it.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                ServiceChat(viewmodel = viewmodel, navController = navHostController)
            }
            composable(route = ScreenRoute.ServiceSession.route){
                val viewmodel = it.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                ServiceSession(viewmodel = viewmodel, navController = navHostController)
            }
            composable(route = ScreenRoute.ServiceRate.route){
                Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()) {
                    Text(text = "Not Yet Implemented Sorry....")
                }
            }
            composable(route = ScreenRoute.ServiceVocInfo.route){
                val viewmodel = it.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                ServiceVocInfo(viewmodel = viewmodel, navController = navHostController)
            }
            composable(route = ScreenRoute.ServiceVocOnline.route){
                val viewmodel = it.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                ServiceVocOnline(viewmodel = viewmodel, navController =navHostController )
            }
            composable(deepLinks = listOf(navDeepLink {
                    uriPattern = "jobgig://confirm-gig/{data}"
                    action = Intent.ACTION_VIEW
                }),
                arguments = listOf(navArgument(name = "data"){
                        type = NavType.StringType
                        defaultValue = ""
                    }),
                route = ScreenRoute.GigAlert.route){entry->
                val data = (entry.arguments?.getString("data")?:"").replace("_","/")
                val viewmodel = entry.sharedViewModel<VocConnectViewModel>(navController = navHostController)
                GigAlert(viewModel = viewmodel, navController =navHostController , path =data)
            }
        }
        composable(route = ScreenRoute.ProfileEdit.route){
            val viewmodel:ProfileViewmodel = viewModel()
            ProfileSetting(viewmodel = viewmodel,navHostController)
        }
        composable(route = ScreenRoute.DonateSeek.route){
            val viewmodel:DonateViewModel = viewModel()
            DonateAsk(viewModel = viewmodel,navHostController)
        }
        composable(route = ScreenRoute.DonateWhy.route){
            DonateWhy(navController = navHostController)
        }
        composable(route = ScreenRoute.DonateGive.route,arguments = listOf(
            navArgument(name = "type"){
                type = NavType.StringType
                defaultValue = DonateType.SkillForgeAid.name
            }
        )){
            val type = it.arguments?.getString("type")?.let { it1 -> DonateType.valueOf(it1) }?:DonateType.SkillForgeAid
            val viewmodel:DonateViewModel = viewModel()
            DonateGive(viewModel = viewmodel, navController =navHostController , type = type)
        }
        composable(route = ScreenRoute.VocRequest.route){
            val viewmodel:VocViewmodel = viewModel()
            RequestVerification(viewmodel = viewmodel, navController = navHostController)
        }
        composable(route = ScreenRoute.UploadTutorial.route){
            val viewmodel:TutCreate = viewModel()
            ManageTuts(viewmodel = viewmodel, navController =navHostController )
        }
        composable(route = ScreenRoute.CreateTutorial.route){
            val viewmodel:TutCreate= viewModel()
            CreateTuts(viewmodel = viewmodel, navController = navHostController)
        }
        composable(route = ScreenRoute.HomeEntry.route){
            HomeContainer(navHostController)
        }
        composable(route = ScreenRoute.Notification.route){
            Notifications(navController = navHostController)
        }
    }
}


@Composable
fun ScreenNavMain(mainnav:NavHostController,navHostController: NavHostController,modifier: Modifier){
    NavHost(modifier = modifier,navController = navHostController, startDestination = ScreenRoute.Main.route){
        composable(route = ScreenRoute.Main.route){
            HomeScreen(navController = mainnav)
        }
        composable(route = ScreenRoute.VocDashBoard.route){
            val viewmodel:DashboardViewmodel = viewModel()
            VocationalDashboard(navController = mainnav, viewmodel = viewmodel)
        }
        composable(route = ScreenRoute.Donate.route){
            Donate(navController = mainnav)
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