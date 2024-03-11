package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.component.ActionButton
import com.example.myfitnessapp.ui.component.ScreenTitleRow
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun LessonContentScreen(
    viewModel: LessonContentViewModel,
    onBackPressed: () -> Unit = {},
    onStartButtonClick: (id: String?) -> Unit = {}
) {
    val lesson by viewModel.lesson.observeAsState()
    val exercises by viewModel.exercises.observeAsState(emptyList())
    val buttonLabel by viewModel.buttonLabel.observeAsState("立即開始")
    val sumOfExerciseDuration by viewModel.sumOfExerciseDuration.observeAsState("00:00")
    val sumOfBurnedCalories by viewModel.sumOfBurnedCalories.observeAsState(0)

    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            ScreenTitleRow(
                title = lesson?.name ?: "訓練內容",
                onBackPressed = onBackPressed
            )
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundCornerStatusRow(
                    modifier = Modifier
                        .weight(3f)
                        .height(70.dp),
                    statusList = mutableListOf<Status>().apply {
                        val startTime = lesson?.startTime
                        if (startTime != null) {
                            add(Status("起始時間", startTime))
                        }
                        add(Status("總費時長", sumOfExerciseDuration))
                        add(Status("消耗熱量", "${sumOfBurnedCalories}kcal"))
                    }
                )
            }
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
}

@Preview(showSystemUi = true)
@Composable
fun LessonScreenPreview() {
    val viewModel = LessonContentViewModel("1")
    MyFitnessAppTheme {
        LessonContentScreen(viewModel)
    }
}

