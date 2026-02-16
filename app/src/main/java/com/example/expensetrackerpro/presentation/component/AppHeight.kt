package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppHeight(
    val height250: Dp = 250.dp,

)

val LocalHeight = compositionLocalOf { AppHeight() }