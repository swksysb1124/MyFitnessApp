package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest
import com.example.myfitnessapp.ui.screen.ScreenTitleRow
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.textColor
import com.example.myfitnessapp.util.KeepScreenOn
import com.example.myfitnessapp.util.formattedDuration

@Composable
fun LessonExercisePage(
    viewModel: LessonExerciseViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    val currentExercise by viewModel.currentExercise.observeAsState()
    val timeLeftInSecond by viewModel.timeLeft.collectAsState(0)
    val onExerciseClose by viewModel.onExerciseClose.observeAsState(ExerciseCloseReason.NotYetClose)

    LaunchedEffect(Unit) {
        viewModel.startLesson()
    }

    LaunchedEffect(onExerciseClose) {
        when (onExerciseClose) {
            ExerciseCloseReason.Finished -> {
                onBackPressed()
            }

            ExerciseCloseReason.Stopped -> {
                viewModel.stopLesson()
                onBackPressed()
            }

            ExerciseCloseReason.NotYetClose -> Unit
        }
    }

    KeepScreenOn()
    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Box(modifier.background(backgroundColor)) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(backgroundColor)
            ) {
                if (currentExercise?.content is Exercise) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        painter = painterResource(id = (currentExercise?.content as Exercise).icon),
                        contentDescription = null
                    )
                }
                val activityName = when (currentExercise?.content) {
                    is Exercise -> (currentExercise?.content as Exercise).name
                    is Rest -> (currentExercise?.content as Rest).name
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
                    text = timeLeftInSecond.formattedDuration(),
                    fontSize = 50.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            ScreenTitleRow(
                title = "",
                onBackPressed = {
                    viewModel.stopLesson()
                    onBackPressed()
                }
            )
        }
    }
}
