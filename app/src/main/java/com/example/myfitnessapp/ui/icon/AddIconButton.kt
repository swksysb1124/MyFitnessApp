package com.example.myfitnessapp.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.textColor

@Composable
fun AddIconButton(onAddLesson: () -> Unit) {
    IconButton(
        onClick = onAddLesson
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = textColor
        )
    }
}