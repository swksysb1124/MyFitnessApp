package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.tts.TextToSpeechEngine
import java.util.Locale

@Composable
fun LessonScreen(
    viewModel: LessonViewModel
) {
    val exercises by viewModel.exercises.observeAsState(emptyList())
    val currentExercise by viewModel.currentExercise.observeAsState()
    val timeLeft by viewModel.timeLeft.collectAsState(0)
    val showExerciseList by viewModel.showExerciseList.observeAsState(true)
    val buttonLabel by viewModel.buttonLabel.observeAsState("開始訓練")

    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        if (showExerciseList) {
            LessonDetailPage(exercises, buttonLabel) {
                viewModel.startLesson()
            }
        } else {
            currentExercise?.let {
                LessonExercisePage(it, timeLeft)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LessonScreenPreview() {
    val viewModel = LessonViewModel(
        textToSpeech = object : TextToSpeechEngine {
            override fun setLanguage(locale: Locale) {
                // do nothing
            }

            override fun speak(text: String) {
                // do nothing
            }
        }
    )
    MyFitnessAppTheme {
        LessonScreen(viewModel)
    }
}

