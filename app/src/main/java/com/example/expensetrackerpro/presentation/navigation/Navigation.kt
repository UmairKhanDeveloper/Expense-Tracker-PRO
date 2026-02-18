package com.example.expensetrackerpro.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetrackerpro.presentation.screens.Register.RegisterScreen
import com.example.expensetrackerpro.presentation.screens.forgetpassword.ForgetPasswordScreen
import com.example.expensetrackerpro.presentation.screens.home.HomeScreen
import com.example.expensetrackerpro.presentation.screens.onboarding.OnBoardingScreen
import com.example.expensetrackerpro.presentation.screens.passwordupdate.PasswordUpdateScreen
import com.example.expensetrackerpro.presentation.screens.signup.SignUpScreen
import com.example.expensetrackerpro.presentation.screens.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) { SplashScreen(navController) }
        composable(Screens.OnBoardingScreen.route) { OnBoardingScreen(navController) }
        composable(Screens.HomeScreen.route) { HomeScreen(navController) }
        composable(Screens.SignUpScreen.route) { SignUpScreen(navController) }
        composable(Screens.ForgetPasswordScreen.route) { ForgetPasswordScreen(navController) }
        composable(Screens.PasswordUpdateScreen.route) { PasswordUpdateScreen(navController) }
        composable(Screens.RegisterScreen.route) { RegisterScreen(navController) }
    }

}

sealed class Screens(val route: String, val title: String) {
    object SplashScreen : Screens("SplashScreen", "SplashScreen")
    object OnBoardingScreen : Screens("OnBoardingScreen", "OnBoardingScreen")
    object HomeScreen : Screens("HomeScreen", "HomeScreen")
    object SignUpScreen : Screens("SignUpScreen", "SignUpScreen")
    object ForgetPasswordScreen : Screens("ForgetPasswordScreen", "ForgetPasswordScreen")
    object PasswordUpdateScreen : Screens("PasswordUpdateScreen", "PasswordUpdateScreen")
    object RegisterScreen : Screens("RegisterScreen", "RegisterScreen")

}