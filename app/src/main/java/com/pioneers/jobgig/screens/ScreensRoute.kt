package com.pioneers.jobgig.screens

sealed class ScreenRoute(var route: String){
    object  GetStarted: ScreenRoute("getStarted")
    object  Login: ScreenRoute("login")
    object  ForgetPassword: ScreenRoute("forget_password")
    object  Signup: ScreenRoute("signup")
    object  Auth:ScreenRoute("auth")
    object  Home:ScreenRoute("home")
    object  Main:ScreenRoute("main")

}