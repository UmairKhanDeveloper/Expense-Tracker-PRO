package com.example.expensetrackerpro.presentation.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.expensetrackerpro.google_firebase.GoogleAuthUiClient
import com.example.expensetrackerpro.presentation.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val googleAuthUiClient = remember {
        GoogleAuthUiClient(context)
    }

    val coroutineScope = rememberCoroutineScope()
    val userData = googleAuthUiClient.getSignedInUser()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen")
        if (userData != null) {

            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "User Name:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = userData.username ?: "No Name",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "User UID:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = userData.userId,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 🔴 SIGN OUT BUTTON
            Box(
                modifier = Modifier
                    .size(width = 200.dp, height = 50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .clickable {

                        coroutineScope.launch {

                            googleAuthUiClient.signOut()

                            Toast.makeText(
                                context,
                                "Signed Out Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate(Screens.SignUpScreen.route) {
                                popUpTo(Screens.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign Out",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {

            Text(
                text = "User Not Logged In",
                fontSize = 18.sp,
                color = Color.Red
            )
        }
    }
}

