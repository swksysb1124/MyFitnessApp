package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise

@Composable
fun LessonScreen(viewModel: LessonViewModel) {
    val exercises by viewModel.exercises.observeAsState(emptyList())
    val currentExerciseIndex by viewModel.currentExerciseIndex.observeAsState(0)
    val timerRunning by viewModel.timerRunning.observeAsState(false)
    val timeLeft by viewModel.timeLeft.observeAsState(0L)
    val showExerciseList by viewModel.showExerciseList.observeAsState(true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (showExerciseList) {
            ExerciseList(exercises = exercises, onItemClick = { viewModel.startLesson() })
        } else {
            Text(
                text = "Current Exercise: ${exercises.getOrNull(currentExerciseIndex) ?: ""}",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Time left: ${timeLeft / 1000} seconds",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>, onItemClick: () -> Unit) {
    LazyColumn {
        items(exercises) { exercise ->
            Text(
                text = exercise.name,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable { onItemClick() }
            )
        }
    }
}