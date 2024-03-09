package com.example.myfitnessapp.ui.screen.plan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.screen.ScreenTitleRow
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun MyPlanScreen(
    onLessonClick: () -> Unit = {}
) {
    val lessons = listOf(
        Lesson(name = "晨間運動", startTime = "07:00", duration = "一小時"),
        Lesson(name = "夜間運動", startTime = " 18:00", duration = "30分鐘"),
        Lesson(startTime = " 10:00", duration = "30分鐘")
    )
    Column(
        Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        /** TODO **/
        ScreenTitleRow(
            title = "我的訓練",
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
        LazyColumn(
            Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(lessons) { lesson ->
                LessonRow(
                    modifier = Modifier.clickable(onClick = onLessonClick),
                    name = lesson.name,
                    startTime = lesson.startTime,
                    duration = lesson.duration
                )
            }
        }
    }
}

data class Lesson(
    val name: String? = null,
    val startTime: String,
    val duration: String,
)

@Preview
@Composable
fun MyPlanScreenPreview() {
    MyFitnessAppTheme {
        MyPlanScreen()
    }
}
