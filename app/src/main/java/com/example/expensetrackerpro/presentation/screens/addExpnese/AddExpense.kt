package com.example.expensetrackerpro.presentation.screens.addIncome

import android.os.Build
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.data.local.database.AppDatabase
import com.example.expensetrackerpro.data.local.entity.ExpenseEntity
import com.example.expensetrackerpro.domain.repository.Repository
import com.example.expensetrackerpro.presentation.viewmodel.MainViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpense(navController: NavController) {

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Food") }
    var selectedDate by remember { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mutableStateOf(LocalDate.now())
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    }



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
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            WeekCalendarAddExpense(
                onDateSelected = {
                    selectedDate = it
                }
            )

            Text("Expense Title", color = Color.Gray, fontSize = 14.sp,  modifier = Modifier.padding(start = 20.dp))

            CustomTextField(
                value = title,
                onValueChange = { title = it }
            )

            Text("Amount", color = Color.Gray, fontSize = 14.sp,  modifier = Modifier.padding(start = 20.dp))

            AmountTextField(
                value = amount,
                onValueChange = { amount = it }
            )

            Text("Category", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.padding(start = 20.dp))

            CategorySection(selectedCategory) {
                selectedCategory = it
            }

            Spacer(modifier = Modifier.weight(1f))

            GradientButtonExpense(
                onClick = {

                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun GradientButtonExpense(onClick: () -> Unit) {

    Box(
        modifier = Modifier.padding(horizontal = 20.dp)
            .clickable { onClick() }
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



@Composable
fun WeekCalendarAddExpense(
    onDateSelected: (LocalDate) -> Unit
) {

    var selectedDate by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableStateOf(LocalDate.now())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }
    var currentWeekStart by remember {
        mutableStateOf(
            selectedDate.with(DayOfWeek.MONDAY)
        )
    }

    val formatter = DateTimeFormatter.ofPattern("MMMM - yyyy")

    val weekDates = (0..6).map {
        currentWeekStart.plusDays(it.toLong())
    }

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(18.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {


                Icon(
                    painter = painterResource(id = R.drawable.arrowleft),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        currentWeekStart = currentWeekStart.minusWeeks(1)
                    }
                )


                Text(
                    text = selectedDate.format(formatter),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )


                Icon(
                    painter = painterResource(id = R.drawable.arrowright),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        currentWeekStart = currentWeekStart.plusWeeks(1)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fri", "Sa", "Su")

            Row(Modifier.fillMaxWidth()) {
                daysOfWeek.forEach {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(Modifier.fillMaxWidth()) {

                weekDates.forEach { date ->

                    val isSelected = date == selectedDate
                    val isCurrentMonth = date.month == selectedDate.month

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected)
                                    Color(0xFF2D5BFF)
                                else
                                    Color.Transparent
                            )
                            .clickable {
                                selectedDate = date
                                onDateSelected(date)
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = date.dayOfMonth.toString(),
                            color = when {
                                isSelected -> Color.White
                                !isCurrentMonth -> Color.LightGray
                                else -> Color.Black
                            }
                        )
                    }
                }
            }
        }
    }
}