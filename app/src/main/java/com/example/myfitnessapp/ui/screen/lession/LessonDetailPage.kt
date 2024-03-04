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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.formattedDuration

@Composable
fun LessonDetailPage(
    exercises: List<Exercise>,
    buttonLabel: String,
    onLessonStart: () -> Unit = {},
) {
    val sumOfExerciseDuration = exercises.sumOf { it.duration }.formattedDuration()
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
                Status("消耗熱量", "3千卡")
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        ExerciseList(
            exercises = exercises,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF50727B)
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
    val exercises = LessonExerciseRepository().getExercises()
    MyFitnessAppTheme {
        LessonDetailPage(
            exercises = exercises,
            buttonLabel = "開始訓練",
        ) {

        }
    }
}
