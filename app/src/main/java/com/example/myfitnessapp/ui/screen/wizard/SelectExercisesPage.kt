package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.ui.component.SettingWizardLayout

@Composable
fun SelectExercisesPage(
    exercises: List<Exercise> = Exercise.getAllExercises(),
    isExerciseSelected: (Exercise) -> Boolean,
    onExerciseSelected: (selected: Boolean, exercise: Exercise) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    SettingWizardLayout(
        title = "請選擇想要的運動",
        onBack = onBack,
        onNext = onNext,
        nextEnabled = true
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(exercises) { exercise ->
                ExerciseSelectionRow(
                    exercise,
                    checked = isExerciseSelected(exercise),
                    onCheckedChange = { checked ->
                        onExerciseSelected(checked, exercise)
                    }
                )
            }
        }
    }
}
