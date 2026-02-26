package com.example.expensetrackerpro.presentation.screens.expense

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalExpenseScreen(navController: NavController) {
    Scaffold(
        contentColor = Color(0xFFf8f9fa),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Total Expenses",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            color = Color(0xFF242D35)
                        )
                    )
                }, navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowback),
                        contentDescription = "",
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFfdfdfd)
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf0f1f3))
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color(0xFFf8f9fa))
                    .align(Alignment.BottomCenter)
            ) {
                BottomSheetUI()

            }
            WeekCalendar(onDateSelected = {})

            TotalBudgetUI()
        }
    }

}


@Composable
fun WeekCalendar(
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
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
                    modifier = Modifier.clickable{
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
                    modifier = Modifier.clickable{
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

            // 🔹 WEEK STRIP (LIKE IMAGE)
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


@Composable
fun TotalBudgetUI() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(160.dp)
        ) {

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(CircleShape)
                    .background(Color(0xFFDCDFE3).copy(alpha = 0.50f))
            )

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCDFE3))
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF1E3CFF),
                                Color(0xFF2BB6FF)
                            )
                        )
                    )
            ) {

                Text(
                    text = "$1,600",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "You have spent total\n80% of your budget",
            textAlign = TextAlign.Center,
            color = Color(0xFF1C1C1C),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BottomSheetUI() {

    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf("Spends", "Categories")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color(0xFFF8F9FA))
            .padding(top = 16.dp)
    ) {

        // 🔵 Tab Bar
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color(0xFFF8F9FA),
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = Color(0xFF2F80ED),
                    height = 3.dp
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selectedTab == index) Color.Black else Color.Gray
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TransactionItem(
            title = "Food",
            date = "20 Feb 2024",
            amount = "+ $20",
            vat = "Vat 0.5%",
            method = "Google Pay"
        )

        TransactionItem(
            title = "Uber",
            date = "13 Mar 2024",
            amount = "- $18",
            vat = "Vat 0.8%",
            method = "Cash"
        )
    }
}

@Composable
fun TransactionItem(
    title: String,
    date: String,
    amount: String,
    vat: String,
    method: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Icon Background
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE9ECEF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                tint = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Left Side (Title + Date)
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = date,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        // Right Side (Amount + Method)
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$amount + $vat",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = method,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
    }
}