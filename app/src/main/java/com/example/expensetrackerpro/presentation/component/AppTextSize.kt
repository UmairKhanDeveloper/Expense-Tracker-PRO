package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class AppTextSize(
    val size24: TextUnit = 24.sp,
    val size16: TextUnit = 16.sp,
)

val localTextSize = compositionLocalOf { AppTextSize() }