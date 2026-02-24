package com.example.expensetrackerpro.presentation.screens.splash

import android.content.Context
import android.content.SharedPreferences
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.component.LocalSpace
import com.example.expensetrackerpro.presentation.component.localTextSize
import com.example.expensetrackerpro.presentation.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val space = LocalSpace.current
    val textSize = localTextSize.current
    val logoScale = remember { Animatable(0f) }

    val prefs = PrefsHelper(context)

    val InterFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )

    LaunchedEffect(true) {
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = {
                OvershootInterpolator(2f).getInterpolation(it)
            })
        )
        delay(1500)
        when {
            prefs.isLoggedIn() -> {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
            }
            !prefs.isOnboardingShown() -> {
                navController.navigate(Screens.OnBoardingScreen.route) {
                    popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
            }
            else -> {
                navController.navigate(Screens.SignUpScreen.route) {
                    popUpTo(Screens.SplashScreen.route) { inclusive = true }
                }
            }
        }


    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .scale(logoScale.value)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = space.space40)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.expenseTracker),
                fontFamily = InterFont,
                fontWeight = FontWeight.Bold,
                fontSize = textSize.size24,
                color = colorResource(id = R.color.black)
            )
            Text(
                text = stringResource(id = R.string.smartBudgetingTrackingMadeEasy),
                fontFamily = InterFont,
                fontWeight = FontWeight.Normal,
                fontSize = textSize.size16,
                color = colorResource(id = R.color.text_gray)
            )
        }
    }
}


class PrefsHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val ONBOARDING_SHOWN = "onboarding_shown"
        private const val IS_LOGGED_IN = "is_logged_in"

    }

    fun isOnboardingShown(): Boolean {
        return prefs.getBoolean(ONBOARDING_SHOWN, false)
    }

    fun setOnboardingShown(shown: Boolean) {
        prefs.edit().putBoolean(ONBOARDING_SHOWN, shown).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    fun setLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean(IS_LOGGED_IN, loggedIn).apply()
    }
}