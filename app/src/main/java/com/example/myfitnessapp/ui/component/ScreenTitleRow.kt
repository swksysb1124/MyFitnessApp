package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.icon.BackIconButton
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun ScreenTitleRow(
    title: String,
    icons: @Composable () -> Unit = {},
    onBackPressed: (() -> Unit)? = null,
    background: Color = backgroundColor
) {
    Row(
        Modifier
            .background(background)
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 25.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBackPressed != null) {
                BackIconButton(onBackPressed)
            }
            Text(title, fontSize = 25.sp, color = textColor)
        }
        icons()
    }
}

@Preview(
    backgroundColor = 0xFF35374B,
    showBackground = true,
    apiLevel = 33
)
@Composable
fun ScreenTitleRowPreview() {
    MyFitnessAppTheme {
        /** TODO **/
        ScreenTitleRow(
            title = "訓練內容",
            onBackPressed = {},
            icons = {
                BackIconButton {

                }
            }
        )
    }
}