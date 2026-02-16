package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppOffset(
    val image1X: Dp = (-0).dp,
    val image1Y: Dp = 2.dp,
    val image2X: Dp = 130.dp,
    val image2Y: Dp = 83.dp,
    val elevation4: Dp = 4.dp
)

val LocalOffset = compositionLocalOf { AppOffset() }