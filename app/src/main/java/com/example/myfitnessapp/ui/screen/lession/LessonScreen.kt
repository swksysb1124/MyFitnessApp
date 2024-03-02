package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.util.KeepScreenOn

val backgroundColor = Color(0xFF35374B)
val textColor = Color(0xFFF5F7F8)

@Composable
fun LessonScreen(viewModel: LessonViewModel) {
    KeepScreenOn()

    val exercises by viewModel.exercises.observeAsState(emptyList())
    val currentExercise by viewModel.currentExercise.observeAsState()
    val timeLeft by viewModel.timeLeft.collectAsState(0)
    val showExerciseList by viewModel.showExerciseList.observeAsState(true)

    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (showExerciseList) {
            LessonDetailPage(exercises) {
                viewModel.startLesson()
            }
        } else {
            LessonExercisePage(currentExercise, timeLeft)
        }
    }
}

@Composable
private fun LessonDetailPage(
    exercises: List<Exercise>,
    onLessonStart: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Text(
            text = "此訓練課程包含以下項目：",
            color = textColor,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExerciseList(
            exercises = exercises,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF50727B)
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = { onLessonStart() }
        ) {
            Text(
                text = "開始訓練",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun LessonExercisePage(
    currentExercise: Exercise?,
    timeLeft: Long
) {
    Box(Modifier.background(backgroundColor)) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(backgroundColor)
        ) {
            Text(
                text = currentExercise?.name ?: "",
                fontSize = 40.sp,
                color = textColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = timeLeft.formattedDuration(),
                fontSize = 35.sp,
                color = textColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun ExerciseList(
    modifier: Modifier = Modifier,
    exercises: List<Exercise>,
    onItemClick: () -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(exercises) { index, exercise ->
            ExerciseRow(onItemClick, exercise)
            if (index != exercises.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun ExerciseRow(
    onItemClick: () -> Unit,
    exercise: Exercise
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = exercise.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Text(
            text = exercise.duration.formattedDuration(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
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