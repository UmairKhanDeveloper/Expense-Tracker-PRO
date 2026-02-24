package com.example.expensetrackerpro.presentation.screens.signup

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.firebase.AuthRepositoryImpl
import com.example.expensetrackerpro.firebase.AuthUser
import com.example.expensetrackerpro.firebase.AuthViewModel
import com.example.expensetrackerpro.firebase.ResultState
import com.example.expensetrackerpro.google_firebase.GoogleAuthUiClient
import com.example.expensetrackerpro.google_firebase.SignInResult
import com.example.expensetrackerpro.google_firebase.SignInViewModel
import com.example.expensetrackerpro.presentation.navigation.Screens
import com.example.expensetrackerpro.presentation.screens.splash.PrefsHelper
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavController) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    val viewModel: SignInViewModel = viewModel()
    val state by viewModel.state.collectAsState()
    val prefs = PrefsHelper(context)

    val firebaseAuth = FirebaseAuth.getInstance()
    val authRepo = AuthRepositoryImpl(firebaseAuth, context)
    val authViewModel = AuthViewModel(authRepo)

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isGoogleLoading by remember { mutableStateOf(false) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {

            coroutineScope.launch {

                val signInResult: SignInResult =
                    googleAuthUiClient.signInWithIntent(
                        result.data ?: run {
                            isGoogleLoading = false
                            return@launch
                        }
                    )

                viewModel.onSignInResult(signInResult)
                isGoogleLoading = false

                if (signInResult.data != null) {
                    isGoogleLoading = false
                    prefs.setLoggedIn(true)
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.SignUpScreen.route) { inclusive = true }
                    }
                    viewModel.resetState()
                } else {
                    Toast.makeText(
                        context,
                        "Error: ${signInResult.errorMessage ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        } else {
            isGoogleLoading = false
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val InterFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(top = 64.dp, bottom = 6.dp)
                    .size(88.dp)
            )

            Text(
                text = stringResource(id = R.string.expenseTracker),
                fontFamily = InterFont,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = colorResource(id = R.color.black),
                modifier = Modifier.padding(bottom = 58.5.dp)
            )

            errorMessage?.let { msg ->
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .width(280.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFFFD7D7))
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.warning),
                        contentDescription = "Warning",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = msg,
                        color = Color(0xFFD32F2F),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            CustomAuthTextFieldEmail(
                value = email,
                onValueChange = { email = it },
                hint = "Email",
                icon = Icons.Default.Email,
            )

            CustomAuthTextFieldPassword(
                value = password,
                onValueChange = { password = it },
                hint = "Password",
                icon = R.drawable.lock,
            )

            LoginButton(
                isLoading = isLoading
            ) {
                if (isLoading) return@LoginButton

                errorMessage = null

                when {
                    email.isBlank() || password.isBlank() -> {
                        errorMessage = "All fields are required!"
                        return@LoginButton
                    }

                    !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                        errorMessage = "Invalid Email Address!"
                        return@LoginButton
                    }

                    password.length < 6 -> {
                        errorMessage = "Password must be at least 6 characters!"
                        return@LoginButton
                    }

                    else -> {

                        isLoading = true

                        coroutineScope.launch {

                            authViewModel.loginUser(
                                AuthUser(
                                    username = "",
                                    email = email.trim(),
                                    password = password.trim()
                                )
                            ).collectLatest { result ->

                                when (result) {

                                    ResultState.Loading -> Unit

                                    is ResultState.Error -> {

                                        errorMessage = when (result.error) {

                                            is FirebaseAuthInvalidUserException ->
                                                "No account found with this email!"

                                            is FirebaseAuthInvalidCredentialsException ->
                                                "Incorrect password!"

                                            is FirebaseNetworkException ->
                                                "No internet connection!"

                                            else ->
                                                "Login failed! Please try again."
                                        }

                                        isLoading = false
                                    }

                                    is ResultState.Success<*> -> {

                                        isLoading = false
                                        prefs.setLoggedIn(true)

                                        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

                                        navController.navigate(Screens.HomeScreen.route) {
                                            popUpTo(Screens.SignUpScreen.route) { inclusive = true }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Text(
                text = "FORGOT PASSWORD",
                color = Color(0xFF6B7580),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clickable {

                        if (email.isNotBlank()) {

                            navController.navigate(
                                Screens.ForgetPasswordScreen.route +
                                        "/${android.net.Uri.encode(email)}"
                            )

                        } else {

                            navController.navigate(
                                Screens.ForgetPasswordScreen.route + "/"
                            )
                        }
                    }
            )

            Text(
                text = "Or",
                color = Color(0xFF242D35),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SocialButton(
                text = "CONTINUE WITH GOOGLE",
                icon = R.drawable.flat_color_icons_google
            ) {

                coroutineScope.launch {

                    isGoogleLoading = true

                    val intentSender = googleAuthUiClient.signIn()

                    intentSender?.let {
                        launcher.launch(
                            IntentSenderRequest.Builder(it).build()
                        )
                    } ?: run {
                        isGoogleLoading = false
                    }
                }
            }

            SocialButton(
                text = "CONTINUE WITH FACEBOOK",
                icon = R.drawable.ant_design_apple_filled,
                onClick = {}
            )

            Row {
                Text(
                    text = "Don't have an account? ",
                    color = Color(0xFF242D35),
                    fontSize = 14.sp
                )

                Text(
                    text = "Register here",
                    color = Color(0xFF0E33F3),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.RegisterScreen.route)
                    }
                )
            }
        }

        if (isGoogleLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF1E3CFF))
            }
        }
    }
}
@Composable
fun CustomAuthTextFieldEmail(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: ImageVector,
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
            .padding(bottom = 20.dp)
            .width(280.dp)
            .height(48.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            },

        decorationBox = { innerTextField ->

            val borderColor =
                if (isFocused)
                    Color(0xFF37ABFF)
                else
                    Color(0XFF9BA1A8)

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isFocused) Color.White else colorResource(id = R.color.light_gray))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(Modifier.weight(1f)) {

                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0XFF9BA1A8),
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
fun CustomAuthTextFieldPassword(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: Int
) {

    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,

        visualTransformation =
            if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),

        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black
        ),

        modifier = Modifier
            .padding(bottom = 24.dp)
            .width(280.dp)
            .height(48.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            },

        decorationBox = { innerTextField ->

            val borderColor =
                if (isFocused)
                    Color(0xFF37ABFF)
                else
                    Color(0XFF9BA1A8)

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, borderColor, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isFocused) Color.White else colorResource(id = R.color.light_gray))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(Modifier.weight(1f)) {

                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0XFF9BA1A8),
                            fontSize = 14.sp
                        )
                    }

                    innerTextField()
                }

                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            passwordVisible = !passwordVisible
                        }
                )
            }
        }
    )
}


@Composable
fun LoginButton(
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
            .padding(bottom = 24.dp)
            .width(280.dp)
            .height(48.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false
            )
            .clip(RoundedCornerShape(10.dp))
            .background(gradientBrush)
            .clickable(enabled = !isLoading) { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(22.dp)
            )
        } else {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun SocialButton(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(bottom = 28.dp)
            .width(280.dp)
            .height(48.dp)
            .border(
                1.dp,
                Color(0xFF9BA1A8),
                RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

