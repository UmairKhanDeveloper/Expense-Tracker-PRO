package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppPadding(
    val small: Dp = 8.dp,
)

val LocalPadding = compositionLocalOf { AppPadding() }