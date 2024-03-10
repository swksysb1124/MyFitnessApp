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
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.model.WeekDay
import com.example.myfitnessapp.ui.screen.ScreenTitleRow
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.speakableDuration

@Composable
fun MyPlanScreen(
    onLessonClick: (id: String) -> Unit = {}
) {
    val lessons = listOf(
        Lesson(
            name = "晨間運動",
            id = "1",
            startTime = "07:00",
            duration = 165,
            daysOfWeek = setOf(WeekDay.MON, WeekDay.THU, WeekDay.WED)
        ),
        Lesson(
            name = "夜間運動",
            id = "2",
            startTime = "18:00",
            duration = 150,
            daysOfWeek = setOf(WeekDay.MON, WeekDay.THU, WeekDay.FRI, WeekDay.SAT)
        ),
        Lesson(
            id = "3",
            startTime = "10:00",
            duration = 30 * 60,
            daysOfWeek = setOf(WeekDay.SUN)
        )
    )
    Column(
        Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
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
                    modifier = Modifier.clickable(
                        onClick = { onLessonClick(lesson.id) }
                    ),
                    name = lesson.name,
                    startTime = lesson.startTime,
                    duration = lesson.duration.speakableDuration(),
                    daysOfWeek = lesson.daysOfWeek
                )
            }
        }
    }
}

@Preview
@Composable
fun MyPlanScreenPreview() {
    MyFitnessAppTheme {
        MyPlanScreen()
    }
}
