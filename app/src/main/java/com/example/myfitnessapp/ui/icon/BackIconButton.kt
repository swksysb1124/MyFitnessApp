package com.example.myfitnessapp.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackIconButton(onBackPressed: () -> Unit) {
    IconButton(
        onClick = { onBackPressed() }
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            tint = Color.White
        )
    }
}