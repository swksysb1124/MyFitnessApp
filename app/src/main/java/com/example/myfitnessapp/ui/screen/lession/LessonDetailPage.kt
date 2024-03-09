package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.myfitnessapp.ui.screen.textColor
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
        Text(
            text = "今日訓練",
            color = textColor,
            fontSize = 30.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
        )
        RoundCornerStatusRow(
            statusList = listOf(
                Status("訓練時間", sumOfExerciseDuration),
                Status("消耗熱量", "${sumCaloriesBurned}千卡")
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExerciseList(
            exercises = exercises,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 20.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonBackgroundColor
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = { onLessonStart() }
        ) {
            Text(
                text = buttonLabel,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
fun LessonDetailPagePreview() {
    val exercises = LessonExerciseRepository().getActivities()
    MyFitnessAppTheme {
        LessonDetailPage(
            exercises = exercises,
            buttonLabel = "開始訓練",
        ) {

        }
    }
}
