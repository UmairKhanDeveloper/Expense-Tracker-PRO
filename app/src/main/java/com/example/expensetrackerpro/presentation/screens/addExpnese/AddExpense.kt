package com.example.expensetrackerpro.presentation.screens.addIncome

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.navigation.Screens
import com.example.expensetrackerpro.presentation.screens.add.dashedBorder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(navController: NavController) {

    var incomeTitle by remember { mutableStateOf("Side Business") }
    var amount by remember { mutableStateOf("1,368") }
    var selectedCategory by remember { mutableStateOf("Rewards") }

    Scaffold(
        containerColor = Color(0xFFF0F1F3),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Add Expense",
                        fontSize = 18.sp,
                        color = Color(0xFF242D35),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painterResource(id = R.drawable.arrowback),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            WeekCalendarAddIncome(onDateSelected = {})

            Text(
                text = "Income Title",
                color = Color.Gray,
                fontSize = 14.sp
            )

            CustomTextField(
                value = incomeTitle,
                onValueChange = { incomeTitle = it }
            )

            Text(
                text = "Amount",
                color = Color.Gray,
                fontSize = 14.sp
            )

            AmountTextField(
                value = amount,
                onValueChange = { amount = it }
            )

            Text(
                text = "Income Category",
                color = Color.Gray,
                fontSize = 14.sp
            )

            CategorySection(selectedCategory) {
                selectedCategory = it
            }

            Spacer(modifier = Modifier.weight(1f))

            GradientButtonExpense()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



@Composable
fun GradientButtonExpense() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color(0xFF2563EB),
                        Color(0xFF38BDF8)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "ADD Expense",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}


