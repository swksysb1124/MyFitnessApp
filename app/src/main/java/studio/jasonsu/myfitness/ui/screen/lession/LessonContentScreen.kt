package studio.jasonsu.myfitness.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import studio.jasonsu.myfitness.datasource.MocKLessonDataSource
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.MockProfileRepository
import studio.jasonsu.myfitness.ui.component.ActionButton
import studio.jasonsu.myfitness.ui.component.ScreenTitleRow
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

@Composable
fun LessonContentScreen(
    viewModel: LessonContentViewModel,
    onBackPressed: () -> Unit = {},
    onStartButtonClick: (id: String?) -> Unit = {}
) {
    val lesson by viewModel.lesson.collectAsStateWithLifecycle()
    val exercises by viewModel.exercises.collectAsStateWithLifecycle()

    val title = lesson?.name?.takeIf { it.isNotEmpty() } ?: "訓練內容"
    val startTime = lesson?.startTime?.toString() ?: "-"
    val generateWeekDescription = lesson?.daysOfWeek.orEmpty()
        .let(DayOfWeek.Companion::generateWeekDescription)
        .let { " | $it" }

    val sumOfExerciseDuration by viewModel.sumOfExerciseDuration.collectAsStateWithLifecycle()
    val sumOfBurnedCalories by viewModel.sumOfBurnedCalories.collectAsStateWithLifecycle()
    val buttonLabel by viewModel.buttonLabel.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ScreenTitleRow(
            title = title,
            onBackPressed = onBackPressed
        )
        StartTimeInfoRow(
            startTime = startTime,
            generateWeekDescription = generateWeekDescription,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 20.dp, bottom = 20.dp)
        )
        RoundCornerStatusRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(80.dp),
            statusList = mutableListOf<Status>().apply {
                add(Status("運動時間", sumOfExerciseDuration))
                add(Status("消耗熱量", sumOfBurnedCalories))
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExerciseList(
            exercises = exercises,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp,
                )
        )
        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(70.dp),
            actionName = buttonLabel,
            onAction = {
                onStartButtonClick(viewModel.id)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun StartTimeInfoRow(
    startTime: String,
    generateWeekDescription: String,
    modifier: Modifier = Modifier
) {
    val textColor = MaterialTheme.colorScheme.onSurface
    Column(modifier) {
        Text(
            text = "$startTime 開始",
            color = textColor, fontSize = 35.sp
        )
        Text(
            text = generateWeekDescription,
            color = textColor, fontSize = 22.sp
        )
    }
}

@Preview(showSystemUi = true, apiLevel = 33)
@Composable
fun LessonScreenPreview() {
    val dataSource = MocKLessonDataSource()
    val lessonRepository = LessonRepository(dataSource)
    val lessonExerciseRepository = LessonExerciseRepository(dataSource)
    val profileRepository = MockProfileRepository()
    val viewModel = LessonContentViewModel("1", lessonRepository, lessonExerciseRepository, profileRepository)
    MyFitnessAppTheme {
        LessonContentScreen(viewModel)
    }
}

