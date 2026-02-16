package com.example.expensetrackerpro.presentation.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.component.localTextSize
import com.example.expensetrackerpro.presentation.navigation.Screens
import kotlinx.coroutines.launch

data class OnboardingScreen(
    val title: String,
    @DrawableRes val res: Int,
    val des: String
)

@Composable
fun OnBoardingScreen(navController: NavController) {
    val list = listOf(
        OnboardingScreen(
            "Note Down Expenses",
            R.drawable.first,
            "Daily note your expenses to help manage money"
        ),
        OnboardingScreen(
            "Simple Money Management",
            R.drawable.second,
            "Get your notifications or alert when you do the over expenses"
        ),
        OnboardingScreen(
            "Easy to Track and Analyze",
            R.drawable.third,
            "Tracking your expense help make sure you don't overspend"
        )
    )

    IntroScreen(list = list) {
        navController.navigate(Screens.HomeScreen.route) {
            popUpTo(Screens.OnBoardingScreen.route) { inclusive = true }
        }
    }
}

@Composable
fun IntroScreen(
    list: List<OnboardingScreen>,
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        list.size
    }

    val coroutineScope = rememberCoroutineScope()
    val textSize = localTextSize.current

    val InterFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(id = R.string.expenseTracker),
                fontFamily = InterFont,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.black),
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }


        Spacer(modifier = Modifier.height(40.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                AsyncImage(
                    model = list[page].res,
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = list[page].title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = list[page].des,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ),
                    color = colorResource(id = R.color.text_gray),
                    textAlign = TextAlign.Center,
                )

            }
        }
        Row(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(list.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(8.dp)
                        .width(if (pagerState.currentPage == index) 24.dp else 24.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                Color(0xFF2962FF)
                            else
                                Color.LightGray
                        )
                )
            }
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < list.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onFinish()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF2962FF),
                                Color(0xFF00C6FF)
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (pagerState.currentPage < list.size - 1)
                        "Next"
                    else
                        "LET’S GO",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

