package com.example.myfitnessapp.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CloseIconButton(
    tint: Color = LocalContentColor.current,
    onEditExit: () -> Unit
) {
    IconButton(
        onClick = onEditExit
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Outlined.Close,
            contentDescription = null,
            tint = tint
        )
    }
}