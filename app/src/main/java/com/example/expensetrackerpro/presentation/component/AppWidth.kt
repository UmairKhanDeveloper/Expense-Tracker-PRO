package com.example.expensetrackerpro.presentation.component

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class AppWidth(
    val width200: Dp = 200.dp,
)

val LocalWidth = compositionLocalOf { AppWidth() }