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

    private fun startExerciseTimer() {
        viewModelScope.launch {
            _timerRunning.value = true
            val initialTime = 30000L // 30 seconds for example
            _timeLeft.value = initialTime

            while ((_timeLeft.value ?: 0) > 0) {
                delay(1000)
                _timeLeft.value = _timeLeft.value?.minus(1000)
            }

            _timerRunning.value = false

            if ((_currentExerciseIndex.value ?: 0) < (_exercises.value?.size ?: 0) - 1) {
                _currentExerciseIndex.value = (_currentExerciseIndex.value ?: 0) + 1
                _timeLeft.value = initialTime
                startExerciseTimer()
            } else {
                _showExerciseList.value = true
                _currentExerciseIndex.value = 0
            }
        }
    }
}