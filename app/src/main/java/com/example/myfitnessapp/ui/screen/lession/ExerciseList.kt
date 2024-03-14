package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.ExerciseType
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExerciseList(
    modifier: Modifier = Modifier,
    exercises: List<Exercise>,
    onItemClick: () -> Unit = {}
) {
    if (exercises.isEmpty()) {
        ExerciseEmptyView()
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(exercises, key = { it.type.type }) { exercise ->
                ExerciseRow(
                    exercise = exercise,
                    modifier = Modifier.animateItemPlacement(), // no working now...
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
private fun ExerciseEmptyView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "無訓練課程",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun ExerciseListPreview() {
    MyFitnessAppTheme {
        ExerciseList(
            exercises = listOf(
                Exercise.create(ExerciseType.Squat, 30),
                Exercise.create(ExerciseType.Bridge, 30)
            )
        )
    }
}