package com.example.expensetrackerpro.presentation.screens.forgetpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.component.LocalHeight
import com.example.expensetrackerpro.presentation.component.LocalOffset
import com.example.expensetrackerpro.presentation.component.LocalPadding
import com.example.expensetrackerpro.presentation.component.LocalRadius
import com.example.expensetrackerpro.presentation.component.LocalSpace
import com.example.expensetrackerpro.presentation.component.LocalWidth
import com.example.expensetrackerpro.presentation.component.localTextSize
import com.example.expensetrackerpro.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(navController: NavController) {

    val padding = LocalPadding.current
    val height = LocalHeight.current
    val width = LocalWidth.current
    val space = LocalSpace.current
    val radius = LocalRadius.current
    val textSize = localTextSize.current
    val offset = LocalOffset.current

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Scaffold(
        containerColor = colorResource(id = R.color.white),
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.white)
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.button),
                            contentDescription = "",
                            tint = colorResource(id = R.color.black)
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal =24.dp ),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Create Your New Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2B2F36)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your new password must be different\nfrom previous password.",
                fontSize = 14.sp,
                color = Color(0xFF9AA0A6)
            )

            Spacer(modifier = Modifier.height(36.dp))

            CustomAuthTextFieldForgetPassword(
                value = password,
                onValueChange = { password = it },
                hint = "Password",
                icon = R.drawable.lock,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomAuthTextFieldForgetPassword(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                hint = "Confirm Password",
                icon = R.drawable.lock,
            )

            Spacer(modifier = Modifier.height(24.dp))

            ValidationRow(true, "Must not contain your name or email")
            ValidationRow(false, "At least 8 characters")
            ValidationRow(false, "Contains a symbol or a number")

            Spacer(modifier = Modifier.weight(1f))

            ResetPassword(onClick = {navController.navigate(Screens.PasswordUpdateScreen.route)})

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ValidationRow(selected: Boolean, text: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "",
            tint = if (selected) Color(0xFF2962FF) else Color(0xFF9AA0A6)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = if (selected)
                Color(0xFF2962FF)
            else
                Color(0xFF9AA0A6)
        )
    }
}

@Composable
fun CustomAuthTextFieldForgetPassword(
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
            .fillMaxWidth()
            .height(52.dp)
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
fun ResetPassword(
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
            text = "RESET PASSWORD",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
