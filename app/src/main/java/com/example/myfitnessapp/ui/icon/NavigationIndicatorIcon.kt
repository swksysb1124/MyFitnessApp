package com.example.myfitnessapp.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.myfitnessapp.ui.color.containerBackgroundColor

@Composable
fun NavigationIndicatorIcon() {
    Icon(
        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
        contentDescription = null,
        tint = containerBackgroundColor
    )
}