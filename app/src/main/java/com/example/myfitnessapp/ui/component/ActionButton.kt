package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    actionName: String,
    onAction: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        onClick = { onAction() }
    ) {
        Text(
            text = actionName,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}