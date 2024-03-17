package com.example.myfitnessapp.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.textColor

@Composable
fun EditIconButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            tint = textColor
        )
    }
}