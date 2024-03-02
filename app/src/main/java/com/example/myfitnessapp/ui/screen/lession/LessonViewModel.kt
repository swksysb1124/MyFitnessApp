package com.example.myfitnessapp.ui.screen.lession

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.domain.LessonManager
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LessonViewModel(
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository()
) : ViewModel() {
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _currentExercise = MutableLiveData<Exercise>()
    val currentExercise: LiveData<Exercise> = _currentExercise

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft = _timeLeft.asStateFlow()

    private val _showExerciseList = MutableLiveData(true)
    val showExerciseList: LiveData<Boolean> = _showExerciseList

    private val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
    private val lessonManager: LessonManager = LessonManager(
        repository = lessonExerciseRepository,
        onCurrentExerciseChange = { _, exercise ->
            _currentExercise.value = exercise
        },
        onTimeLeft = { timeLeftInMs ->
            if (timeLeftInMs <= 3000) {
                toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200)
            }
            _timeLeft.value = timeLeftInMs
        },
        onLessonStart = {
            _showExerciseList.value = false
        },
        onLessonFinished = {
            _showExerciseList.value = true
        }
    )

    init {
        _exercises.value = lessonManager.exercises
    }

    fun startLesson() {
        viewModelScope.launch {
            lessonManager.startLesson()
        }
    }
}