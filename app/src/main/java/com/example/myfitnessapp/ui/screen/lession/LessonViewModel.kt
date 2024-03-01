package com.example.myfitnessapp.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.Exercise
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LessonViewModel : ViewModel() {
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _currentExerciseIndex = MutableLiveData(0)
    val currentExerciseIndex: LiveData<Int> = _currentExerciseIndex

    private val _timerRunning = MutableLiveData(false)
    val timerRunning: LiveData<Boolean> = _timerRunning

    private val _timeLeft = MutableLiveData(0L)
    val timeLeft: LiveData<Long> = _timeLeft

    private val _showExerciseList = MutableLiveData(true)
    val showExerciseList: LiveData<Boolean> = _showExerciseList

    init {
        _exercises.value = listOf(
            Exercise("Squats", TimeUnit.SECONDS.toMillis(30)),
            Exercise("Push-ups", TimeUnit.SECONDS.toMillis(30)),
            Exercise("Plank", TimeUnit.SECONDS.toMillis(60))
        )
    }

    fun startLesson() {
        _showExerciseList.value = false
        startExerciseTimer()
    }

    private fun getCurrentExerciseOrNull(): Exercise? =
        _currentExerciseIndex.value
            ?.let { _exercises.value?.get(it) }


    private fun startExerciseTimer() {
        val currentExercise = getCurrentExerciseOrNull() ?: return
        viewModelScope.launch {
            // start timer
            _timerRunning.value = true
            // countdown timer
            _timeLeft.value = currentExercise.duration
            while ((_timeLeft.value ?: 0) > 0) {
                delay(1000)
                _timeLeft.value = _timeLeft.value?.minus(1000)
            }
            // stop timer
            _timerRunning.value = false

            if (hasNextExercise()) {
                _currentExerciseIndex.value = (_currentExerciseIndex.value ?: 0) + 1
                startExerciseTimer()
            } else {
                _showExerciseList.value = true
                _currentExerciseIndex.value = 0
            }
        }
    }

    private fun hasNextExercise(): Boolean =
        (_currentExerciseIndex.value ?: 0) < (_exercises.value?.size ?: 0) - 1
}