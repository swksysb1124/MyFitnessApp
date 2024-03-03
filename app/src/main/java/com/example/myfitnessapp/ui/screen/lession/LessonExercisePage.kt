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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.util.KeepScreenOn

@Composable
fun LessonExercisePage(
    currentExercise: Exercise?,
    timeLeft: Long
) {
    KeepScreenOn()
    Box(Modifier.background(backgroundColor)) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(backgroundColor)
        ) {
            if (currentExercise?.icon != null) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    painter = painterResource(id = currentExercise.icon),
                    contentDescription = null
                )
            }
            Text(
                text = currentExercise?.name ?: "",
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