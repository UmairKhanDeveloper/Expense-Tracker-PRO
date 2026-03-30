package com.example.expensetrackerpro.presentation.screens.add

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerpro.R
import com.example.expensetrackerpro.presentation.navigation.Screens
import com.example.expensetrackerpro.presentation.screens.home.LatestEntriesHeader
import com.example.expensetrackerpro.presentation.screens.home.TransactionList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {

    Scaffold(
        contentColor = Color(0xFFf8f9fa),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 18.sp,
                            color = Color(0xFF242D35)
                        )
                    )
                }, navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowback),
                            contentDescription = "",
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }

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
                    .height(550.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color(0xFFf8f9fa))
                    .align(Alignment.BottomCenter)
            ) {

                LatestEntriesHeader()

                TransactionList()
            }

            IncomeExpenseButtons(navController, onAddClick = {})

        }

    }
}


@Composable
fun IncomeExpenseButtons(
    navController: NavController,
    onAddClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFF3F4F6))
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {


            Box(
                modifier = Modifier
                    .height(88.dp)
                    .width(48.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .dashedBorder()
                    .clickable { onAddClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 24.sp,
                    color = Color.Gray
                )
            }


        Box(
            modifier = Modifier
                .weight(1f)
                .height(88.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE9EAEC))
                .clickable {navController.navigate(Screens.AddIncome.route) },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet_send_svgrepo_com),
                    contentDescription = "", modifier = Modifier.size(24.dp), tint = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add Income",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(88.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2D6BFF),
                            Color(0xFF39C3FF)
                        )
                    )
                )
                .clickable {navController.navigate(Screens.AddExpense.route) },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wallet_receive_svgrepo_com),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp), tint = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Add Expense",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

fun Modifier.dashedBorder(): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidth = 2.dp.toPx()
        val dashWidth = 10.dp.toPx()
        val dashGap = 10.dp.toPx()

        drawRoundRect(
            color = Color.Gray,
            size = size,
            cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx()),
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashWidth, dashGap), 0f
                )
            )
        )
    }
)