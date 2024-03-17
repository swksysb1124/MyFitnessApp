package com.example.myfitnessapp.ui.screen.wizard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.datasource.MocKLessonDataSource
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.ui.component.SettingWizardLayout
import com.example.myfitnessapp.ui.component.WizardType
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun LessonContentConfirmPage(
    viewModel: AddLessonViewModel,
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val name by viewModel.name.observeAsState(AddLessonViewModel.DEFAULT_LESSON_NAME)
    val startTime by viewModel.startTime.observeAsState(Lesson.DefaultStartTime)
    val selectedDaysOfWeek = remember { viewModel.selectedDaysOfWeek }
    val selectedExercises = remember { viewModel.selectedExercises }

    SettingWizardLayout(
        title = "請確認你的訓練",
        type = WizardType.Confirm,
        onBack = onBack,
        onConfirm = {
            viewModel.save {
                onConfirm()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            LessonContentRow(
                label = "名稱",
                name = name
            )
            LessonContentRow(
                label = "開始時間",
                name = "$startTime\n${DayOfWeek.generateWeekDescription(selectedDaysOfWeek)}"
            )
            LessonContentRow(
                label = "運動內容",
                name = selectedExercises.joinToString("、") { it.name }
            )
        }
    }
}

@Composable
private fun LessonContentRow(
    label: String,
    name: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            )
        )
        Text(
            name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 20.sp
        )
    }
}

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun LessonSettingConfirmPagePreview() {
    val dataSource = MocKLessonDataSource()
    val lessonRepository = LessonRepository(dataSource)
    val lessonExerciseRepository = LessonExerciseRepository(dataSource)
    val viewModel = AddLessonViewModel(lessonRepository, lessonExerciseRepository)
    MyFitnessAppTheme {
        LessonContentConfirmPage(
            viewModel = viewModel,
            onConfirm = {},
            onBack = {}
        )
    }
}