package studio.jasonsu.myfitness.ui.screen.wizard.add.lesson

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.ui.component.SettingWizardLayout

@Composable
fun SelectExercisesPage(
    exerciseMetaData: List<ExerciseMetaData> = ExerciseMetaData.All,
    isExerciseSelected: (ExerciseMetaData) -> Boolean,
    onExerciseSelected: (selected: Boolean, exercise: ExerciseMetaData) -> Unit,
    nextEnabled: Boolean = true,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    SettingWizardLayout(
        title = "請選擇想要的運動",
        onBack = onBack,
        onNext = onNext,
        nextOrConfirmEnabled = nextEnabled
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(exerciseMetaData) { metaData ->
                ExerciseSelectionRow(
                    metaData = metaData,
                    checked = isExerciseSelected(metaData),
                    onCheckedChange = { checked ->
                        onExerciseSelected(checked, metaData)
                    }
                )
            }
        }
    }
}
