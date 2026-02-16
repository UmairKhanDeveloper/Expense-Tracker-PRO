package com.example.expensetrackerpro.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

data class AppRadius(
    val roundedSmall: RoundedCornerShape = RoundedCornerShape(8.dp),
)

val LocalRadius = compositionLocalOf { AppRadius() }