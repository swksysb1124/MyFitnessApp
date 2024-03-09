package com.example.myfitnessapp.ui.screen.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.rowBackgroundColor
import com.example.myfitnessapp.ui.screen.textColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun LessonRow(
    modifier: Modifier = Modifier,
    name: String? = null,
    startTime: String,
    duration: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(rowBackgroundColor, shape = RoundedCornerShape(20.dp))
            .height(120.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            if (name != null) {
                Text(text = name, fontSize = 16.sp, color = backgroundColor)
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(startTime, fontSize = 30.sp, color = textColor, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(5.dp))
            Text(duration, fontSize = 16.sp, color = textColor)
        }
        Box {
            Text("日 一 二 三 四 五 六", fontSize = 18.sp, color = backgroundColor)
        }
    }
}

@Preview(widthDp = 300)
@Composable
fun LessonRowPreview() {
    MyFitnessAppTheme {
        LessonRow(
            name ="晨間運動",
            startTime = "18:00",
            duration = "一小時"
        )
    }
}