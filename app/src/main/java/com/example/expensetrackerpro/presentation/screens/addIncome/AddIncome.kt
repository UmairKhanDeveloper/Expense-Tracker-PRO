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
import com.example.expensetrackerpro.presentation.screens.add.dashedBorder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIncome(navController: NavController) {

    var incomeTitle by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Rewards") }

    Scaffold(
        containerColor = Color(0xFFF0F1F3),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Add Income",
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
                color = Color(0xFF9BA1A8),
                fontSize = 14.sp
            )

            CustomTextField(
                value = incomeTitle,
                onValueChange = { incomeTitle = it }
            )

            Text(
                text = "Amount",
                color = Color(0xFF9BA1A8),
                fontSize = 14.sp
            )

            AmountTextField(
                value = amount,
                onValueChange = { amount = it }
            )

            Text(
                text = "Income Category",
                color = Color(0xFF9BA1A8),
                fontSize = 14.sp
            )

            CategorySection(selectedCategory) {
                selectedCategory = it
            }

            Spacer(modifier = Modifier.weight(1f))

            GradientButton()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Enter Income Title"
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
                    .background(
                        if (isFocused)
                            Color.White
                        else
                            colorResource(id = R.color.light_gray)
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

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
fun AmountTextField(
    value: String,
    onValueChange: (String) -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
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
                    .background(
                        if (isFocused)
                            Color.White
                        else
                            colorResource(id = R.color.light_gray)
                    )
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(Modifier.weight(1f)) {

                    if (value.isEmpty()) {
                        Text(
                            text = "Enter Amount",
                            color = Color(0XFF9BA1A8),
                            fontSize = 14.sp
                        )
                    }

                    innerTextField()
                }

                Text(
                    text = "$",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        }
    )
}

@Composable
fun CategorySection(
    selected: String,
    onSelected: (String) -> Unit
) {

    Column {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFF1F3F6))
                    .dashedBorder()
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Category",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }

            CategoryChip(
                title = "Salary",
                isSelected = selected == "Salary",
                onClick = { onSelected("Salary") }
            )

            CategoryChip(
                title = "Rewards",
                isSelected = selected == "Rewards",
                onClick = { onSelected("Rewards") }
            )
        }
    }
}

@Composable
fun CategoryChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {


    Box(
        modifier = Modifier
            .height(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = if (isSelected) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1E3CFF),
                            Color(0xFF2ED3FF)
                        )
                    )
                } else {
                    SolidColor(Color.White)
                },
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color.White else Color.DarkGray
        )
    }
}
@Composable
fun GradientButton() {

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
            "ADD INCOME",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Composable
fun WeekCalendarAddIncome(
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
