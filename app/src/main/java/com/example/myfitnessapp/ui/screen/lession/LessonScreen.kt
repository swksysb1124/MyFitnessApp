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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise

val backgroundColor = Color(0xFF35374B)
val textColor = Color(0xFFF5F7F8)

@Composable
fun LessonScreen(viewModel: LessonViewModel) {
    val exercises by viewModel.exercises.observeAsState(emptyList())
    val currentExerciseIndex by viewModel.currentExerciseIndex.observeAsState(0)
    val timerRunning by viewModel.timerRunning.observeAsState(false)
    val timeLeft by viewModel.timeLeft.observeAsState(0L)
    val showExerciseList by viewModel.showExerciseList.observeAsState(true)

    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (showExerciseList) {
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
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.startLesson() }) {
                    Text(text = "開始訓練", fontSize = 20.sp)
                }
            }
        } else {
            Box(Modifier.background(backgroundColor)) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(backgroundColor)
                ) {
                    Text(
                        text = exercises.getOrNull(currentExerciseIndex)?.name ?: "",
                        fontSize = 40.sp,
                        color = textColor,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "${timeLeft / 1000} 秒",
                        fontSize = 35.sp,
                        color = textColor,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
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
        items(exercises) { exercise ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick() },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = exercise.name,
                    fontSize = 20.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
                Text(
                    text = exercise.duration.div(1000).toString(),
                    fontSize = 20.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                )
            }
        }
    }
}