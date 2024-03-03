package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun ExerciseList(
    modifier: Modifier = Modifier,
    exercises: List<Exercise>,
    onItemClick: () -> Unit = {}
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(exercises) { index, exercise ->
            ExerciseRow(onItemClick, exercise)
            Divider()
        }
    }
}

@Preview
@Composable
fun ExerciseListPreview() {
    val lessonViewModelFactory = LessonExerciseRepository()
    MyFitnessAppTheme {
        ExerciseList(exercises = lessonViewModelFactory.getExercises())
    }
}