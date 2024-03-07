package com.example.myfitnessapp.ui.screen.lession

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.domain.LessonManager
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.util.speakableDuration
import com.example.myfitnessapp.util.tts.TextToSpeakUtil
import com.example.myfitnessapp.util.tts.TextToSpeechEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val remindToNextTimeInSecond = 5

class LessonViewModel(
    private val textToSpeech: TextToSpeechEngine,
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository()
) : ViewModel() {
    private val _exercises = MutableLiveData<List<Activity<Exercise>>>()
    val exercises: LiveData<List<Activity<Exercise>>> = _exercises

    private val _currentExercise = MutableLiveData<Activity<*>>()
    val currentExercise: LiveData<Activity<*>> = _currentExercise

    private val _timeLeftInSecond = MutableStateFlow(0)
    val timeLeft = _timeLeftInSecond.asStateFlow()

    private val _showExerciseList = MutableLiveData(true)
    val showExerciseList: LiveData<Boolean> = _showExerciseList

    private val _buttonLabel = MutableLiveData("開始訓練")
    val buttonLabel: LiveData<String> = _buttonLabel

    private val lessonManager: LessonManager = LessonManager(
        repository = lessonExerciseRepository,
        onActivityChange = ::onActivityChange,
        onActivityTimeLeft = ::onActivityTimeLeft,
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
        _exercises.value = lessonManager.activities
    }

    fun startLesson() {
        viewModelScope.launch {
            lessonManager.startLesson()
        }
    }

    private fun onActivityChange(index: Int, exercise: Activity<*>) {
        speakExerciseStarted(exercise)
        _currentExercise.value = exercise
    }

    private fun onActivityTimeLeft(timeLeftInSecond: Int, activity: Activity<*>) {
        if (timeLeftInSecond <= remindToNextTimeInSecond) {
            speakExerciseTimeLeft(timeLeftInSecond)
        }
        _timeLeftInSecond.value = timeLeftInSecond
    }

    private fun speakExerciseStarted(activity: Activity<*>) {
        val wording = when (val content = activity.content) {
            is Exercise -> "開始${content.name}，${activity.durationInSecond.speakableDuration()}"
            is Rest -> "進入${content.name}，${activity.durationInSecond.speakableDuration()}"
            else -> return
        }
        textToSpeech.speak(wording)
    }

    private fun speakExerciseTimeLeft(timeLeftInSecond: Int) {
        textToSpeech.speak(timeLeftInSecond.toString())
    }

    private fun speakLessonFinished() {
        textToSpeech.speak("訓練結束")
    }
}

@Suppress("UNCHECKED_CAST")
class LessonViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val textToSpeech = TextToSpeakUtil.create(application)
        return LessonViewModel(textToSpeech) as T
    }
}