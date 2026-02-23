package com.example.expensetrackerpro.presentation.screens.Register

import android.widget.Toast
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
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.firebase.AuthRepositoryImpl
import com.example.expensetrackerpro.firebase.AuthUser
import com.example.expensetrackerpro.firebase.AuthViewModel
import com.example.expensetrackerpro.firebase.ResultState
import com.example.expensetrackerpro.firebase_realtimedatabase.RealTimeDbRepository
import com.example.expensetrackerpro.firebase_realtimedatabase.RealTimeUser.RealTimeItems
import com.example.expensetrackerpro.firebase_realtimedatabase.RealTimeViewModel
import com.example.expensetrackerpro.presentation.navigation.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val InterFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )
    val firebaseAuth = FirebaseAuth.getInstance()
    val authRepo = AuthRepositoryImpl(firebaseAuth, context)
    val authViewModel = AuthViewModel(authRepo)

    val databaseReference = FirebaseDatabase.getInstance().reference.child("users")
    val realTimeRepo = remember { RealTimeDbRepository(databaseReference, context) }
    val realTimeViewModel = remember { RealTimeViewModel(realTimeRepo) }

    val interFont = FontFamily(
        Font(R.font.inter18ptregular, FontWeight.Normal),
        Font(R.font.inter18ptbold, FontWeight.Bold)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))

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

        Text(
            text = "Create Your Account",
            fontFamily = interFont,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color(0xFF01E3CFF),
            modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)
        )

        errorMessage?.let { msg ->
            Row(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .width(280.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFD7D7))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.warning),
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = msg,
                    color = Color(0xFFD32F2F),
                    fontSize = 13.sp
                )
            }
        }


        CustomAuthTextFieldUserName(
            value = username,
            onValueChange = {
                username = it
                errorMessage = null
            },
            hint = "Enter your full name",
            icon = R.drawable.profile
        )

        CustomAuthTextFieldEmail(
            value = email,
            onValueChange = {
                email = it
                errorMessage = null
            },
            hint = "Enter your email address",
            icon = Icons.Default.Email
        )

        CustomAuthTextFieldPassword(
            value = password,
            onValueChange = {
                password = it
                errorMessage = null
            },
            hint = "Create a strong password",
            icon = R.drawable.lock
        )


        LoginButton(
            isLoading = isLoading
        ) {

            errorMessage = null

            when {

                username.isBlank() || email.isBlank() || password.isBlank() -> {
                    errorMessage = "All fields are required!"
                    return@LoginButton
                }

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> {
                    errorMessage = "Invalid email address!"
                    return@LoginButton
                }

                password.length < 6 -> {
                    errorMessage = "Password must be at least 6 characters!"
                    return@LoginButton
                }

                else -> {

                    isLoading = true

                    scope.launch {
                        authViewModel.createUser(
                            AuthUser(
                                username = username.trim(),
                                email = email.trim(),
                                password = password.trim()
                            )
                        ).collectLatest { result ->

                            when (result) {

                                is ResultState.Error -> {
                                    errorMessage =
                                        result.error.message ?: "Registration failed!"
                                    isLoading = false
                                }

                                ResultState.Loading -> Unit

                                is ResultState.Success<*> -> {

                                    val firebaseUser =
                                        FirebaseAuth.getInstance().currentUser

                                    firebaseUser?.let { user ->

                                        realTimeViewModel.insert(
                                            RealTimeItems(
                                                userFirstName = username.trim(),
                                                email = user.email ?: email.trim(),
                                                password = password.trim()
                                            )
                                        ).collectLatest { dbResult ->

                                            when (dbResult) {

                                                is ResultState.Error -> {
                                                    errorMessage =
                                                        dbResult.error.message
                                                            ?: "Database error!"
                                                    isLoading = false
                                                }

                                                ResultState.Loading -> Unit

                                                is ResultState.Success<*> -> {

                                                    Toast.makeText(
                                                        context,
                                                        "Registration successful! Please log in.",
                                                        Toast.LENGTH_SHORT
                                                    ).show()

                                                    FirebaseAuth.getInstance().signOut()
                                                    isLoading = false

                                                    navController.navigate(
                                                        Screens.SignUpScreen.route
                                                    ) {
                                                        popUpTo(
                                                            Screens.RegisterScreen.route
                                                        ) { inclusive = true }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = "Already have an account? ",
                fontSize = 14.sp
            )

            Text(
                text = "Sign In",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E3CFF),
                modifier = Modifier.clickable {
                    navController.navigate(Screens.SignUpScreen.route)
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun CustomAuthTextFieldUserName(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: Int,
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
            }
        }
    )
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
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = "SIGN UP",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

