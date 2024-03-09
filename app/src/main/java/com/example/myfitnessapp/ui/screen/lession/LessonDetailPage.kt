package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.domain.CaloriesBurnedCalculator
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.buttonBackgroundColor
import com.example.myfitnessapp.ui.screen.plan.ScreenTitleRow
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.formattedDuration
import kotlin.math.roundToInt

@Composable
fun LessonDetailPage(
    exercises: List<Activity<Exercise>>,
    buttonLabel: String,
    onLessonStart: () -> Unit = {},
) {
    val calculator = CaloriesBurnedCalculator()
    val sumOfExerciseDuration = exercises.sumOf { it.durationInSecond }.formattedDuration()
    val sumCaloriesBurned = exercises.sumOf {
        val exercise = it.content
        calculator.calculate(
            mets = exercise.mets,
            weightInKg = 80.0,
            mins = (it.durationInSecond.toDouble() / 60.0)
        )
    }.roundToInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ScreenTitleRow(title = "訓練內容")
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundCornerStatusRow(
                modifier = Modifier.weight(2f).height(70.dp),
                statusList = listOf(
                    Status("時間", sumOfExerciseDuration),
                    Status("消耗", "${sumCaloriesBurned}kcal")
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            StartButton(
                modifier = Modifier.weight(1f).height(70.dp),
                onLessonStart = onLessonStart,
                buttonLabel
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
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun StartButton(
    modifier: Modifier = Modifier,
    onLessonStart: () -> Unit,
    buttonLabel: String
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        onClick = { onLessonStart() }
    ) {
        Text(
            text = buttonLabel,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Preview
@Composable
fun LessonDetailPagePreview() {
    val exercises = LessonExerciseRepository().getActivities()
    MyFitnessAppTheme {
        LessonDetailPage(
            exercises = exercises,
            buttonLabel = "開始",
        ) {

        }
    }
}
