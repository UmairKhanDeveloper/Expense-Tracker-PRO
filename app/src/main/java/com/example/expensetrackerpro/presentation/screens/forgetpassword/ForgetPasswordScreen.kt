package com.example.expensetrackerpro.presentation.screens.forgetpassword

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.navigation.Screens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    navController: NavController,
    email: String
) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var emailState by rememberSaveable { mutableStateOf(email) }
    var message by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = colorResource(id = R.color.white),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.button),
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Forgot Password",
                fontSize = 26.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your email to receive reset link",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(30.dp))

            EmailTextField(
                value = emailState,
                onValueChange = { emailState = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("success", true))
                        Color(0xFF2E7D32) else Color.Red,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ResetPasswordButton(
                isLoading = isLoading
            ) {

                message = ""

                when {
                    emailState.isBlank() -> {
                        message = "Email cannot be empty"
                        return@ResetPasswordButton
                    }

                    !android.util.Patterns.EMAIL_ADDRESS
                        .matcher(emailState.trim())
                        .matches() -> {
                        message = "Invalid email format"
                        return@ResetPasswordButton
                    }
                }

                isLoading = true

                firebaseAuth
                    .sendPasswordResetEmail(emailState.trim())
                    .addOnCompleteListener { task ->

                        isLoading = false

                        if (task.isSuccessful) {

                            message = "Success! Reset link sent."

                            Handler(Looper.getMainLooper()).postDelayed({

                                navController.navigate(
                                    Screens.PasswordUpdateScreen.route
                                ) {
                                    popUpTo(Screens.ForgetPasswordScreen.route) {
                                        inclusive = true
                                    }
                                }

                            }, 800)

                        } else {
                            message = task.exception?.message ?: "Something went wrong"
                        }
                    }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .onFocusChanged { isFocused = it.isFocused },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        1.dp,
                        if (isFocused) Color.Blue else Color.Gray,
                        RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.light_gray))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = "Enter Email",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun ResetPasswordButton(
    isLoading: Boolean,
    onClick: () -> Unit
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1E3CFF),
            Color(0xFF2ED3FF)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(8.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(gradientBrush)
            .clickable(enabled = !isLoading) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = "RESET PASSWORD",
                color = Color.White
            )
        }
    }
}