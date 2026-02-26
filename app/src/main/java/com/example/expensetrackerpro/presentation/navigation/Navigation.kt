package com.example.expensetrackerpro.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetrackerpro.presentation.screens.Register.RegisterScreen
import com.example.expensetrackerpro.presentation.screens.add.AddScreen
import com.example.expensetrackerpro.presentation.screens.expense.TotalExpenseScreen
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
        composable(Screens.TotalExpenseScreen.route) { TotalExpenseScreen(navController) }
    }
}

sealed class Screens(
    val route: String,
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector
) {

    object SplashScreen : Screens(
        "SplashScreen",
        "Splash",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object OnBoardingScreen : Screens(
        "OnBoardingScreen",
        "OnBoarding",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object HomeScreen : Screens(
        "HomeScreen",
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home
    )

    object TaskScreen : Screens(
        "TaskScreen",
        "Tasks",
        Icons.Filled.CheckCircle,
        Icons.Outlined.CheckCircle
    )

    object NotificationScreen : Screens(
        "NotificationScreen",
        "Notifications",
        Icons.Filled.Notifications,
        Icons.Outlined.Notifications
    )

    object SettingScreen : Screens(
        "SettingScreen",
        "Settings",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object AddScreen : Screens(
        "AddScreen",
        "Add",
        Icons.Filled.Add,
        Icons.Outlined.Add
    )

    object SignUpScreen : Screens(
        "SignUpScreen",
        "Signup",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object ForgetPasswordScreen : Screens(
        "ForgetPasswordScreen",
        "Forget",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object PasswordUpdateScreen : Screens(
        "PasswordUpdateScreen",
        "Update",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object RegisterScreen : Screens(
        "RegisterScreen",
        "Register",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )

    object TotalExpenseScreen : Screens(
        "TotalExpenseScreen",
        "TotalExpenseScreen",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )
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
            .height(70.dp)
    ) {

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(70.dp),
            containerColor = Color(0xFFf8f9fa),
            tonalElevation = 0.dp
        ) {

            items.forEachIndexed { index, screen ->

                if (index == 2) {
                    Spacer(modifier = Modifier.width(70.dp))
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
                                        containerColor = Color.Red,
                                        modifier = Modifier.size(8.dp)
                                    ) {}
                                }
                            }
                        ) {
                            val isSelected = currentRoute == screen.route

                            Icon(
                                imageVector = if (isSelected)
                                    screen.filledIcon
                                else
                                    screen.outlinedIcon,
                                contentDescription = screen.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    label = {},
                    alwaysShowLabel = false,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF2962FF),
                        unselectedIconColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF1E3CFF),
                Color(0xFF2BB6FF)
            )
        )

        FloatingActionButton(
            onClick = {
                navController.navigate(Screens.AddScreen.route) {
                    launchSingleTop = true
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-10).dp)
                .size(64.dp)
                .zIndex(1f),
            shape = CircleShape,
            containerColor = Color.Transparent,
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(gradientBrush),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
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

        Screens.TotalExpenseScreen.route -> false
        else -> true
    }

    if (showBottomNav) {

        Scaffold(
            bottomBar = {
                BottomNavigation(navController)
            }
        ) {
            Navigation(navController)

        }

    } else {
        Navigation(navController)
    }
}