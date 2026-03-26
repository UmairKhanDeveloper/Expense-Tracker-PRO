package com.example.expensetrackerpro.presentation.screens.goal

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.navigation.BottomNavigation
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddGoal(navController: NavController) {

    var goal by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    var showSheet by remember { mutableStateOf(false) }
    var selectedDeadLine by remember { mutableStateOf("Yearly") }

    var showDateDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    val isBlurActive = showSheet || showDateDialog

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(if (isBlurActive) 5.dp else 0.dp)
        ) {

            Scaffold(
                containerColor = Color(0xFFf8f9fa),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "Add Goal",
                                color = Color(0xFF242D35),
                                fontSize = 18.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.arrowback),
                                    contentDescription = ""
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFfdfdfd)
                        )
                    )
                },
                bottomBar = {
                    Column {
                        Divider(thickness = 2.dp, color = Color(0xFFF0F1F3))
                        BottomNavigation(navController)
                    }
                }
            ) { padding ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFf0f1f3))
                        .padding(padding)
                ) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        Text(
                            text = "Goal Title",
                            color = Color(0xFF9BA1A8),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 32.dp, start = 24.dp, bottom = 12.dp)
                        )

                        CustomTextField(
                            value = goal,
                            onValueChange = { goal = it },
                            hint = "Goal Title"
                        )

                        Text(
                            text = "Amount",
                            color = Color(0xFF9BA1A8),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
                        )

                        CustomTextFieldAmount(
                            value = amount,
                            onValueChange = { amount = it },
                            hint = "Amount",
                            icon = R.drawable.amount,
                            onClick = {}
                        )

                        Text(
                            text = "Contribution Type",
                            color = Color(0xFF9BA1A8),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
                        )

                        CustomTextFieldContributionType(
                            value = selectedDeadLine,
                            onValueChange = {},
                            hint = "Contribution Type",
                            icon = R.drawable.chevron_down,
                            onClick = { showSheet = true }
                        )

                        Text(
                            text = "Deadline",
                            color = Color(0xFF9BA1A8),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 24.dp, bottom = 12.dp)
                        )

                        CustomTextFieldDeadline(
                            value = selectedDate,
                            onValueChange = {},
                            hint = "Deadline",
                            icon = R.drawable.calendar,
                            onClick = { showDateDialog = true }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        GradientButtonAddGoal()
                    }
                }
            }
        }

        if (isBlurActive) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }

        if (showSheet) {
            AlertDialog(
                onDismissRequest = { showSheet = false },
                shape = RoundedCornerShape(20.dp),
                containerColor = Color(0xFFF5F5F5),
                text = {

                    val options = listOf("Daily", "Weekly", "Monthly", "Yearly")

                    Column {
                        options.forEachIndexed { index, option ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedDeadLine = option
                                        showSheet = false
                                    }
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = option,
                                    color = if (option == selectedDeadLine)
                                        Color(0xFF2F5BFF) else Color.Black
                                )

                                if (option == selectedDeadLine) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.check),
                                        contentDescription = null,
                                    )
                                }
                            }

                            if (index != options.lastIndex) {
                                Divider()
                            }
                        }
                    }
                },
                confirmButton = {}
            )
        }

        if (showDateDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                MonthCalendar(
                    onDateSelected = { date ->
                        selectedDate = date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                        showDateDialog = false
                    }
                )
            }
        }

    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
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
            .padding(start = 24.dp, bottom = 24.dp)
            .width(300.dp)
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
fun CustomTextFieldAmount(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: Int,
    onClick: () -> Unit
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
            .padding(start = 24.dp, bottom = 24.dp)
            .width(300.dp)
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
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0XFF9BA1A8),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = if (isFocused) Color(0xFF37ABFF) else Color(0xFF9BA1A8)
                    )
                }

            }
        }
    )
}

@Composable
fun CustomTextFieldContributionType(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: Int,
    onClick: () -> Unit
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
            .padding(start = 24.dp, bottom = 24.dp)
            .width(300.dp)
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
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0XFF9BA1A8),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = if (isFocused) Color(0xFF37ABFF) else Color(0xFF9BA1A8)
                    )
                }

            }
        }
    )
}

@Composable
fun CustomTextFieldDeadline(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    icon: Int,
    onClick: () -> Unit
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
            .padding(start = 24.dp, bottom = 180.dp)
            .width(300.dp)
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
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color(0XFF9BA1A8),
                            fontSize = 14.sp
                        )
                    }
                    innerTextField()
                }
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = if (isFocused) Color(0xFF37ABFF) else Color(0xFF9BA1A8)
                    )
                }

            }
        }
    )
}


@Composable
fun GradientButtonAddGoal() {

    Box(
        modifier = Modifier
            .padding(start = 24.dp)
            .width(300.dp)
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
            "ADD Goal",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthCalendar(
    onDateSelected: (LocalDate) -> Unit,
    initialSelectedDate: LocalDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) LocalDate.now() else TODO()
) {
    var selectedDate by remember { mutableStateOf(initialSelectedDate) }
    var currentMonthStart by remember { mutableStateOf(selectedDate.withDayOfMonth(1)) }

    val formatter = DateTimeFormatter.ofPattern("MMMM - yyyy")

    val firstDayOfWeek = DayOfWeek.MONDAY
    val firstDayToShow = currentMonthStart.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))

    val lastDayOfMonth = currentMonthStart.with(TemporalAdjusters.lastDayOfMonth())
    val lastDayToShow = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

    val totalDays = ChronoUnit.DAYS.between(firstDayToShow, lastDayToShow).toInt() + 1

    val daysToDisplay = (0 until totalDays).map { firstDayToShow.plusDays(it.toLong()) }

    val daysOfWeek = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

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
                    contentDescription = "Previous Month",
                    modifier = Modifier.clickable {
                        currentMonthStart = currentMonthStart.minusMonths(1)
                    }
                )

                Text(
                    text = currentMonthStart.format(formatter),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Icon(
                    painter = painterResource(id = R.drawable.arrowright),
                    contentDescription = "Next Month",
                    modifier = Modifier.clickable {
                        currentMonthStart = currentMonthStart.plusMonths(1)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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

            Column {
                daysToDisplay.chunked(7).forEach { week ->
                    Row(Modifier.fillMaxWidth()) {
                        week.forEach { date ->
                            val isSelected = date == selectedDate
                            val isCurrentMonth = date.month == currentMonthStart.month

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(4.dp)
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFF2D5BFF) else Color.Transparent)
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
    }
}