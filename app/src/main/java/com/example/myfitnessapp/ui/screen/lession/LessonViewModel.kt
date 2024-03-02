package com.example.myfitnessapp.ui.screen.lession

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.Exercise
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LessonViewModel : ViewModel() {
    private val internalExercises = mutableListOf<Exercise>()
    private var currentExerciseIndex = 0

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _currentExercise = MutableLiveData<Exercise>()
    val currentExercise: LiveData<Exercise> = _currentExercise

    private val _timerRunning = MutableLiveData(false)
    val timerRunning: LiveData<Boolean> = _timerRunning

    private val _timeLeft = MutableLiveData(0L)
    val timeLeft: LiveData<Long> = _timeLeft

    private val _showExerciseList = MutableLiveData(true)
    val showExerciseList: LiveData<Boolean> = _showExerciseList

    private val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)

    init {
        val exercises = getExercises()
        _exercises.value = exercises
        initInternalExercises(
            exercises = exercises,
            rest = Exercise("休息時間", TimeUnit.SECONDS.toMillis(10))
        )
    }

    private fun initInternalExercises(
        exercises: List<Exercise>,
        rest: Exercise
    ) {
        val result = mutableListOf<Exercise>().apply {
            exercises.onEachIndexed { index, exercise ->
                add(exercise)
                if (index != exercises.lastIndex) {
                    add(rest)
                }
            }
        }
        internalExercises.clear()
        internalExercises.addAll(result)
    }

    private fun getExercises() = listOf(
        Exercise("深蹲", TimeUnit.SECONDS.toMillis(30)),
        Exercise("伏地挺身", TimeUnit.SECONDS.toMillis(30)),
        Exercise("平板支撐", TimeUnit.SECONDS.toMillis(60)),
    )

    fun startLesson() {
        _showExerciseList.value = false
        startExercise()
    }

    private fun getCurrentExercise(): Exercise = internalExercises[currentExerciseIndex]


    private fun startExercise() {
        val currentExercise = getCurrentExercise()
        _currentExercise.value = currentExercise
        viewModelScope.launch {
            startTimer(currentExercise.duration) { timeLeftInMs ->
                if (timeLeftInMs <= 3000) {
                    toneGenerator.startTone(ToneGenerator.TONE_CDMA_ABBR_ALERT, 200)
                }
            }
            if (hasNextExercise()) {
                currentExerciseIndex += 1
                startExercise()
            } else {
                _showExerciseList.value = true
                currentExerciseIndex = 0
            }
        }
    }

    private suspend fun startTimer(
        duration: Long,
        onTimeLeft: (Long) -> Unit = {}
    ) {
        // start timer
        _timerRunning.value = true
        // countdown timer
        _timeLeft.value = duration
        onTimeLeft(duration)

        while ((_timeLeft.value ?: 0) > 0) {
            delay(1000)
            _timeLeft.value = _timeLeft.value?.minus(1000)
            onTimeLeft(_timeLeft.value ?: 0)
        }
        // stop timer
        _timerRunning.value = false
    }

    private fun hasNextExercise(): Boolean =
        currentExerciseIndex < internalExercises.size - 1
}