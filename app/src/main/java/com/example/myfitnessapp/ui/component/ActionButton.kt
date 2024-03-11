package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.buttonBackgroundColor

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    actionName: String,
    onAction: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        onClick = { onAction() }
    ) {
        Text(
            text = actionName,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}