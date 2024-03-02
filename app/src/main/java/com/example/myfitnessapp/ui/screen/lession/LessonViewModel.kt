package com.example.myfitnessapp.ui.screen.lession

import android.app.Application
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.domain.LessonManager
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

private const val remindToNextTime = 5000

class LessonViewModel(
    application: Application,
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository()
) : AndroidViewModel(application) {
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _currentExercise = MutableLiveData<Exercise>()
    val currentExercise: LiveData<Exercise> = _currentExercise

    private val _timeLeft = MutableStateFlow(0L)
    val timeLeft = _timeLeft.asStateFlow()

    private val _showExerciseList = MutableLiveData(true)
    val showExerciseList: LiveData<Boolean> = _showExerciseList

    private var isTextToSpeechInit = false
    private lateinit var textToSpeech: TextToSpeech

    private val lessonManager: LessonManager = LessonManager(
        repository = lessonExerciseRepository,
        onExerciseChange = ::onExerciseChange,
        onExerciseTimeLeft = ::onExerciseTimeLeft,
        onLessonStart = { _showExerciseList.value = false },
        onLessonFinished = { _showExerciseList.value = true }
    )

    init {
        _exercises.value = lessonManager.exercises
        textToSpeech = TextToSpeech(application.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("TAG", "TextToSpeech init success")
                textToSpeech.setLanguage(Locale.TRADITIONAL_CHINESE)
                isTextToSpeechInit = true
            }
        }
    }

    private fun onExerciseChange(index: Int, exercise: Exercise) {
        speak(exercise.name)
        _currentExercise.value = exercise
    }

    private fun onExerciseTimeLeft(timeLeftInMs: Long, exercise: Exercise) {
        if (timeLeftInMs <= remindToNextTime) {
            speak((timeLeftInMs / 1000).toString())
        }
        _timeLeft.value = timeLeftInMs
    }

    private fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun startLesson() {
        viewModelScope.launch {
            lessonManager.startLesson()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class LessonViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LessonViewModel(application) as T
    }
}