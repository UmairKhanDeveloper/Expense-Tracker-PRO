package com.example.expensetrackerpro.presentation.screens.signup

import android.app.Activity
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
import com.example.expensetrackerpro.google_firebase.GoogleAuthUiClient
import com.example.expensetrackerpro.google_firebase.SignInResult
import com.example.expensetrackerpro.google_firebase.SignInViewModel
import com.example.expensetrackerpro.presentation.navigation.Screens
import kotlinx.coroutines.launch




@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleAuthUiClient = remember { GoogleAuthUiClient(context) }
    val viewModel: SignInViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            coroutineScope.launch {
                val signInResult: SignInResult =
                    googleAuthUiClient.signInWithIntent(result.data ?: return@launch)
                viewModel.onSignInResult(signInResult)

                // Show toast
                if (signInResult.data != null) {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.HomeScreen.route)
                    viewModel.resetState()
                } else {
                    Toast.makeText(
                        context,
                        "Error: ${signInResult.errorMessage ?: "Unknown error"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }



    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val InterFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )

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

        LoginButton(onClick = {})

        Text(
            text = "FORGOT PASSWORD",
            color = Color(0xFF6B7580),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable { navController.navigate(Screens.ForgetPasswordScreen.route) }
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
                val intentSender = googleAuthUiClient.signIn()
                intentSender?.let {
                    launcher.launch(IntentSenderRequest.Builder(it).build())
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
                text = "Don’t have an account? ",
                color = Color(0xFF242D35),
                fontSize = 14.sp
            )

            Text(
                text = "Register here",
                color = Color(0xFF0E33F3),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController.navigate(Screens.RegisterScreen.route) }
            )
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
                    .background(if (isFocused) Color.White else colorResource(id = R.color.light_gray))                    .padding(horizontal = 12.dp),
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
                    .background(if (isFocused) Color.White else colorResource(id = R.color.light_gray))                    .padding(horizontal = 12.dp),
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
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Login",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
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

