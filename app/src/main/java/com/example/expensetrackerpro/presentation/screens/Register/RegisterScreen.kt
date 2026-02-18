package com.example.expensetrackerpro.presentation.screens.Register

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.expensetrackerpro.presentation.navigation.Screens

@Composable
fun RegisterScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
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
            color = colorResource(id = R.color.black), modifier = Modifier.padding(bottom = 58.5.dp)
        )


        CustomAuthTextFieldUserName(
            value = username,
            onValueChange = { username = it },
            hint = "Username",
            icon = R.drawable.profile,
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
            text = "SIGN UP",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


