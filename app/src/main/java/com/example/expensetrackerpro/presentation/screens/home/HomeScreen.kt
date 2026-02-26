package com.example.expensetrackerpro.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.expensetrackerpro.google_firebase.GoogleAuthUiClient
import com.example.expensetrackerpro.presentation.navigation.BottomNavigation
import com.example.expensetrackerpro.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val googleAuthUiClient = remember {
        GoogleAuthUiClient(context)
    }

    val userData = googleAuthUiClient.getSignedInUser()
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        contentColor = Color(0xFFf8f9fa),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Overview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF1E3CFF),
                                    Color(0xFF2BB6FF)
                                )
                            )),
                        contentAlignment = Alignment.Center
                    ) {
                        if (userData?.profilePictureUrl != null) {
                            AsyncImage(
                                model = userData.profilePictureUrl,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = androidx.compose.ui.layout.ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFfdfdfd)
                )
            )
        }, bottomBar = {

                Column {
                    Divider(
                        thickness = 2.dp,
                        color = Color(0xFFF0F1F3)
                    )

                    BottomNavigation(navController)
                }

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
                    .height(460.dp)
                    .background(Color(0xFFf8f9fa))
                    .align(Alignment.BottomCenter)
            ) {
                SavingsRemindBudgetBar(
                    selectedIndex = selectedIndex,
                    onTabSelected = { selectedIndex = it }
                )


                PagerIndicator(
                    currentIndex = selectedIndex,
                    total = 3
                )

                LatestEntriesHeader()

                TransactionList()

            }

            StatsCardsSection(navController = navController)
        }
    }
}

@Composable
fun StatsCardsSection(navController: NavController) {

    var selectedIndex by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        StatCard(
            title = "Total Salary",
            amount = "$1,289.38",
            isSelected = selectedIndex == 1
        ) {
            selectedIndex = 1
        }

        StatCard(
            title = "Total Expense",
            amount = "$298.16",
            isSelected = selectedIndex == 2
        ) {
            selectedIndex = 2
            navController.navigate(Screens.TotalExpenseScreen.route)

        }

        StatCard(
            title = "Monthly",
            amount = "$3,380.00",
            isSelected = selectedIndex == 3
        ) {
            selectedIndex = 3
        }
    }
}

@Composable
fun StatCard(
    title: String,
    amount: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

   val  brush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1E3CFF),
            Color(0xFF2BB6FF)
        )
    )

    val background = if (isSelected) brush else SolidColor(Color(0xFFFFFFFF))
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .width(124.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .background(background)
            .clickable { onClick() }
            .padding(vertical = 18.dp, horizontal = 14.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Column {

                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = contentColor.copy(alpha = 0.7f)
                )
            }

            Text(
                text = amount,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}

@Composable
fun SavingsRemindBudgetBar(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {

    val items = listOf(
        TabItem("Savings", Icons.Default.Add),
        TabItem("Remind", Icons.Default.Notifications),
        TabItem("Budget", Icons.Default.AccountBalanceWallet)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items.forEachIndexed { index, item ->

            val isSelected = selectedIndex == index

            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF1E3CFF),
                    Color(0xFF2BB6FF)
                )
            )



            val backgroundBrush =
                if (isSelected) gradientBrush
                else SolidColor(Color(0xFFFFFFFF))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .shadow(
                        elevation = if (isSelected) 1.dp else 1.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .background(backgroundBrush)
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected)
                                    Color.White.copy(alpha = 0.25f)
                                else
                                    Color(0xFFEBEEF0)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) Color.White else Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun PagerIndicator(
    currentIndex: Int,
    total: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        repeat(total) { index ->

            val isSelected = currentIndex == index

            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .height(6.dp)
                    .width(if (isSelected) 20.dp else 20.dp)
                    .clip(RoundedCornerShape(50))
                    .background(
                        if (isSelected)
                            Color(0xFF2962FF)
                        else
                            Color(0xFFD9D9D9)
                    )
            )
        }
    }
}

@Composable
fun LatestEntriesHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Latest Entries",
            color = Color(0xFF1C1C1E),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Transparent)
                .border(
                    width = 2.dp,
                    color = Color(0xFFB0B8BF),
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = null,
                tint = Color(0xFF2C2C2E),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun TransactionItem(
    icon: ImageVector,
    title: String,
    date: String,
    amount: String,
    method: String,
    isIncome: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFF1F1F3)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF111111)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = date,
                    fontSize = 13.sp,
                    color = Color(0xFF8E8E93)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = amount,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color =  Color(0xFF111111)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = method,
                fontSize = 13.sp,
                color = Color(0xFF8E8E93)
            )
        }
    }
}

@Composable
fun TransactionList() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        TransactionItem(
            icon = Icons.Default.Fastfood,
            title = "Food",
            date = "20 Feb 2024",
            amount = "+ $20 + Vat 0.5%",
            method = "Google Pay",
            isIncome = true
        )

        TransactionItem(
            icon = Icons.Default.DirectionsBike,
            title = "Uber",
            date = "13 Mar 2024",
            amount = "- $18 + Vat 0.8%",
            method = "Cash",
            isIncome = false
        )

        TransactionItem(
            icon = Icons.Default.ShoppingBag,
            title = "Shopping",
            date = "11 Mar 2024",
            amount = "- $400 + Vat 0.12%",
            method = "Paytm",
            isIncome = false
        )
    }
}