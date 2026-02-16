package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSpace(
    val space40: Dp = 40.dp,
)

val LocalSpace = compositionLocalOf { AppSpace() }