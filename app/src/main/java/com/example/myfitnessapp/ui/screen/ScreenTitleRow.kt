package com.example.myfitnessapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun ScreenTitleRow(
    title: String,
    icon: @Composable () -> Unit = {},
    onBackPressed: (() -> Unit)? = null
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 25.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBackPressed != null) {
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
            Text(title, fontSize = 25.sp, color = textColor)
        }
        icon()
    }
}

@Preview(backgroundColor = 0xFF35374B, showBackground = true)
@Composable
fun ScreenTitleRowPreview() {
    MyFitnessAppTheme {
        ScreenTitleRow(
            title = "訓練內容",
            onBackPressed = {},
            icon = {
                IconButton(
                    onClick = { /** TODO **/ }
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )
    }
}