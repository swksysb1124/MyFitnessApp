package studio.jasonsu.myfitness.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import studio.jasonsu.myfitness.domain.LessonManager
import studio.jasonsu.myfitness.model.Activity
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.Rest
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.util.spokenDuration
import studio.jasonsu.myfitness.util.tts.TextToSpeechEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val remindToNextTimeInSecond = 5

class LessonExerciseViewModel(
    private val lessonId: String?,
    private val textToSpeech: TextToSpeechEngine,
    private val lessonExerciseRepository: LessonExerciseRepository
) : ViewModel() {
    private val _currentExercise = MutableLiveData<Activity>()
    val currentExercise: LiveData<Activity> = _currentExercise

    private val _timeLeftInSecond = MutableStateFlow(0)
    val timeLeft = _timeLeftInSecond.asStateFlow()

    private val _onExerciseClose = MutableLiveData(ExerciseCloseReason.NotYetClose)
    val onExerciseClose: LiveData<ExerciseCloseReason> = _onExerciseClose

    private lateinit var lessonManager: LessonManager
    init {
        viewModelScope.launch {
            val activities = lessonExerciseRepository.getActivities(lessonId)
            lessonManager = LessonManager(
                activities = activities,
                onActivityChange = ::onActivityChange,
                onActivityTimeLeft = ::onActivityTimeLeft,
                onLessonStart = {},
                onLessonStop = {
                    speakLessonStopped()
                    _onExerciseClose.value = ExerciseCloseReason.Stopped
                },
                onLessonFinished = {
                    speakLessonFinished()
                    _onExerciseClose.value = ExerciseCloseReason.Finished
                }
            )
            startLesson()
        }
    }

    private fun startLesson() {
        viewModelScope.launch {
            lessonManager.startLesson()
        }
    }

    fun stopLesson() {
        viewModelScope.launch {
            lessonManager.stopLesson()
        }
    }

    private fun onActivityChange(index: Int, exercise: Activity) {
        speakExerciseStarted(exercise)
        _currentExercise.value = exercise
    }

    private fun onActivityTimeLeft(timeLeftInSecond: Int, activity: Activity) {
        if (timeLeftInSecond <= remindToNextTimeInSecond) {
            speakExerciseTimeLeft(timeLeftInSecond)
        }
        _timeLeftInSecond.value = timeLeftInSecond
    }

    private fun speakExerciseStarted(activity: Activity) {
        val wording = when (activity) {
            is Exercise -> "開始${activity.name}，${activity.durationInSecond.spokenDuration()}"
            is Rest -> "進入${activity.name}，${activity.durationInSecond.spokenDuration()}"
            else -> return
        }
        textToSpeech.speak(wording)
    }

    private fun speakExerciseTimeLeft(timeLeftInSecond: Int) {
        textToSpeech.speak(timeLeftInSecond.toString())
    }

    private fun speakLessonFinished() {
        textToSpeech.speak("結束訓練")
    }

    private fun speakLessonStopped() {
        textToSpeech.speak("停止訓練")
    }
}

enum class ExerciseCloseReason {
    Finished, Stopped, NotYetClose
}

