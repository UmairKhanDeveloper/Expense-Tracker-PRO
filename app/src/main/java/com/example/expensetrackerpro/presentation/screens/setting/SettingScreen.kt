package com.example.expensetrackerpro.presentation.screens.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.navigation.BottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {

    Scaffold(
        containerColor = Color(0xFFF5F5F5),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Reminders",
                        color = Color(0xFF242D35),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowback),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFDFDFD)
                )
            )
        },

        bottomBar = {
            Column {
                Divider(thickness = 1.dp, color = Color(0xFFF0F1F3))
                BottomNavigation(navController)
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            ReminderItem(
                title = "Bill Payment",
                amount = "$200",
                dueDate = "3 Jun 2024"
            )

            ReminderItem(
                title = "Car Loan",
                amount = "$600",
                dueDate = "11 July 2024"
            )

            ReminderItem(
                title = "Iphone 15 Pro",
                amount = "$1,000",
                dueDate = "3 Aug 2024"
            )

            ReminderItem(
                title = "New Bike",
                amount = "$2,300",
                dueDate = "12 Sep 2024"
            )
        }
    }
}

@Composable
fun ReminderItem(
    title: String,
    amount: String,
    dueDate: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(14.dp)
    ) {

        // Top Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Reminder Date: 26 May 2024",
                fontSize = 12.sp,
                color = Color(0xFF9AA0A6)
            )
            Icon(
                painter = painterResource(id = R.drawable.more_horizontal),
                contentDescription = ""
            )

        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF242D35)
                )

                Text(
                    text = amount,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "Due on",
                    fontSize = 11.sp,
                    color = Color(0xFF9AA0A6)
                )

                Text(
                    text = dueDate,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}