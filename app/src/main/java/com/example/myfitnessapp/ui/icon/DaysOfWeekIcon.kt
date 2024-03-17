package com.example.myfitnessapp.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.backgroundColor

@Composable
fun DaysOfWeekIcon() {
    Icon(
        modifier = Modifier.size(25.dp),
        tint = backgroundColor,
        imageVector = Icons.Sharp.DateRange,
        contentDescription = null
    )
}