package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.textColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.KeepScreenOn
import com.example.myfitnessapp.util.formattedDuration

@Composable
fun LessonExercisePage(
    currentExercise: Activity<*>,
    timeLeft: Int
) {
    KeepScreenOn()
    Box(Modifier.background(backgroundColor)) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(backgroundColor)
        ) {
            if (currentExercise.content is Exercise) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    painter = painterResource(id = currentExercise.content.icon),
                    contentDescription = null
                )
            }
            val activityName = when (currentExercise.content) {
                is Exercise -> currentExercise.content.name
                is Rest -> currentExercise.content.name
                else -> ""
            }
            Text(
                text = activityName,
                fontSize = 40.sp,
                color = textColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = timeLeft.formattedDuration(),
                fontSize = 50.sp,
                color = textColor,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun LessonExercisePagePreview() {
    val exercise = LessonExerciseRepository().getActivities().first()
    MyFitnessAppTheme {
        LessonExercisePage(
            currentExercise = exercise,
            timeLeft = 60_000,
        )
    }
}
