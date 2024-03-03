package com.example.myfitnessapp.ui.screen.lession

import android.app.Application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.domain.LessonManager
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.util.TextToSpeakUtil
import com.example.myfitnessapp.util.TextToSpeech
import com.example.myfitnessapp.util.speakableDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val remindToNextTime = 5000

class LessonViewModel(
    private val textToSpeech: TextToSpeech,
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

    private val _buttonLabel = MutableLiveData("開始訓練")
    val buttonLabel: LiveData<String> = _buttonLabel

    private val lessonManager: LessonManager = LessonManager(
        repository = lessonExerciseRepository,
        onExerciseChange = ::onExerciseChange,
        onExerciseTimeLeft = ::onExerciseTimeLeft,
        onLessonStart = {
            _showExerciseList.value = false
        },
        onLessonFinished = {
            speakLessonFinished()
            _showExerciseList.value = true
            _buttonLabel.value = "重新訓練"
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

    private fun onExerciseChange(index: Int, exercise: Exercise) {
        speakExerciseStarted(exercise)
        _currentExercise.value = exercise
    }

    private fun onExerciseTimeLeft(timeLeftInMs: Long, exercise: Exercise) {
        if (timeLeftInMs <= remindToNextTime) {
            speakExerciseTimeLeft(timeLeftInMs)
        }
        _timeLeft.value = timeLeftInMs
    }

    private fun speakExerciseStarted(exercise: Exercise) {
        val wording = "開始${exercise.name}，${exercise.duration.speakableDuration()}"
        textToSpeech.speak(wording)
    }

    private fun speakExerciseTimeLeft(timeLeftInMs: Long) {
        textToSpeech.speak((timeLeftInMs / 1000).toString())
    }

    private fun speakLessonFinished() {
        textToSpeech.speak("訓練結束")
    }
}

@Suppress("UNCHECKED_CAST")
class LessonViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val textToSpeech = TextToSpeakUtil.getInstance(application)
        return LessonViewModel(textToSpeech) as T
    }
}