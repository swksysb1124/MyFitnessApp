package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val backgroundColor = Color(0xFF35374B)
val textColor = Color(0xFFF5F7F8)

@Composable
fun LessonScreen(viewModel: LessonViewModel) {
    val exercises by viewModel.exercises.observeAsState(emptyList())
    val currentExercise by viewModel.currentExercise.observeAsState()
    val timeLeft by viewModel.timeLeft.collectAsState(0)
    val showExerciseList by viewModel.showExerciseList.observeAsState(true)
    val buttonLabel by viewModel.buttonLabel.observeAsState("開始訓練")

    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (showExerciseList) {
            LessonDetailPage(exercises, buttonLabel) {
                viewModel.startLesson()
            }
        } else {
            LessonExercisePage(currentExercise, timeLeft)
        }
    }
}

fun Long.formattedDuration(): String {
    val seconds = this / 1000
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val secondLeft = (seconds % 60)
    return if (hour < 1) {
        String.format("%02d:%02d", minute, secondLeft)
    } else {
        String.format("%d:%02d:%02d", hour, minute, secondLeft)
    }
}

fun Long.speakableDuration(): String {
    if (this == 0L) return ""
    val seconds = this / 1000
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val secondLeft = (seconds % 60)
    return buildString {
        if (hour > 0) {
            append("${hour}小時")
        }
        if (minute > 0) {
            append("${minute}分鐘")
        }
        if (secondLeft > 0) {
            append("${secondLeft}秒")
        }
    }
}