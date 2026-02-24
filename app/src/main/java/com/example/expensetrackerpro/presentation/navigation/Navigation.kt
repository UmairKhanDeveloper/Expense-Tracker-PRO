package com.example.expensetrackerpro.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.screens.Register.RegisterScreen
import com.example.expensetrackerpro.presentation.screens.add.AddScreen
import com.example.expensetrackerpro.presentation.screens.forgetpassword.ForgetPasswordScreen
import com.example.expensetrackerpro.presentation.screens.home.HomeScreen
import com.example.expensetrackerpro.presentation.screens.notification.NotificationScreen
import com.example.expensetrackerpro.presentation.screens.onboarding.OnBoardingScreen
import com.example.expensetrackerpro.presentation.screens.passwordupdate.PasswordUpdateScreen
import com.example.expensetrackerpro.presentation.screens.setting.SettingScreen
import com.example.expensetrackerpro.presentation.screens.signup.SignUpScreen
import com.example.expensetrackerpro.presentation.screens.splash.SplashScreen
import com.example.expensetrackerpro.presentation.screens.task.TaskScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route) {
        composable(Screens.SplashScreen.route) { SplashScreen(navController) }
        composable(Screens.OnBoardingScreen.route) { OnBoardingScreen(navController) }
        composable(Screens.HomeScreen.route) { HomeScreen(navController) }
        composable(Screens.SignUpScreen.route) { SignUpScreen(navController) }
        composable(
            route = Screens.ForgetPasswordScreen.route + "/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForgetPasswordScreen(navController, email)
        }
        composable(Screens.PasswordUpdateScreen.route) { PasswordUpdateScreen(navController) }
        composable(Screens.RegisterScreen.route) { RegisterScreen(navController) }
        composable(Screens.TaskScreen.route) { TaskScreen(navController) }
        composable(Screens.AddScreen.route) { AddScreen(navController) }
        composable(Screens.NotificationScreen.route) { NotificationScreen(navController) }
        composable(Screens.SettingScreen.route) { SettingScreen(navController) }
    }
}

sealed class Screens(val route: String, val title: String, val icon: Int) {
    object SplashScreen : Screens("SplashScreen", "SplashScreen", icon = R.drawable.setting)
    object OnBoardingScreen : Screens("OnBoardingScreen", "OnBoardingScreen", icon = R.drawable.setting)
    object HomeScreen : Screens("HomeScreen", "Home", icon = R.drawable.home)
    object SignUpScreen : Screens("SignUpScreen", "SignUpScreen", icon = R.drawable.setting)
    object ForgetPasswordScreen : Screens("ForgetPasswordScreen", "ForgetPasswordScreen", icon = R.drawable.setting)
    object PasswordUpdateScreen : Screens("PasswordUpdateScreen", "PasswordUpdateScreen", icon = R.drawable.setting)
    object RegisterScreen : Screens("RegisterScreen", "RegisterScreen", icon = R.drawable.setting)
    object TaskScreen : Screens("TaskScreen", "Tasks", icon = R.drawable.task)
    object AddScreen : Screens("AddScreen", "Add", icon = R.drawable.setting)
    object NotificationScreen : Screens("NotificationScreen", "Notifications", icon = R.drawable.notification)
    object SettingScreen : Screens("SettingScreen", "Settings", icon = R.drawable.setting)
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Screens.HomeScreen,
        Screens.TaskScreen,
        Screens.NotificationScreen,
        Screens.SettingScreen,
    )

    val navStack by navController.currentBackStackEntryAsState()
    val currentRoute = navStack?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Bottom Navigation Bar
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(64.dp),
            containerColor = Color(0xFFF5F5F5),
            tonalElevation = 0.dp
        ) {
            items.forEachIndexed { index, screen ->
                // Skip middle position for FAB
                if (index == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }

                NavigationBarItem(
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (screen == Screens.NotificationScreen) {
                                    Badge(
                                        containerColor = Color(0xFFFF4444),
                                        contentColor = Color.White
                                    ) {
                                        // Small dot badge
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(screen.icon!!),
                                contentDescription = screen.title
                            )
                        }
                    },
                    label = {

                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2196F3),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color(0xFF2196F3),
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        // Floating Action Button in Center
        FloatingActionButton(
            onClick = {
                navController.navigate(Screens.AddScreen.route) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-8).dp)
                .size(56.dp)
                .zIndex(1f),
            shape = CircleShape,
            containerColor = Color(0xFF2196F3),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavEntry() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomNav = when (currentRoute) {
        Screens.SplashScreen.route,
        Screens.OnBoardingScreen.route,
        Screens.SignUpScreen.route,
        Screens.RegisterScreen.route,
        Screens.ForgetPasswordScreen.route,
        Screens.PasswordUpdateScreen.route -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavigation(navController)
            }
        }
    ) {
        Navigation(navController)
    }
}