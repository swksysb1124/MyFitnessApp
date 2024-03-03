package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myfitnessapp.model.Exercise

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