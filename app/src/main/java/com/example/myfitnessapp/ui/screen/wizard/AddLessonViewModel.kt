package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Exercise

class AddLessonViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String> = _startTime

    private val _weekDescription = MutableLiveData<String>()
    val weekDescription: LiveData<String> = _weekDescription

    private val selectedDaysOfWeek = mutableStateListOf<DayOfWeek>()

    private val selectedExercises = mutableStateListOf<Exercise>()

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateStartTime(startTime: String) {
        _startTime.value = startTime
    }

    fun isDaySelected(dayOfWeek: DayOfWeek): Boolean {
        return selectedDaysOfWeek.contains(dayOfWeek)
    }

    fun onDaySelected(selected: Boolean, dayOfWeek: DayOfWeek) {
        if (selected) {
            selectedDaysOfWeek.add(dayOfWeek)
        } else {
            selectedDaysOfWeek.remove(dayOfWeek)
        }
        val description = DayOfWeek.generateWeekDescription(selectedDaysOfWeek.toList())
        _weekDescription.value = description
    }

    fun isExerciseSelected(exercise: Exercise): Boolean {
        return selectedExercises.contains(exercise)
    }

    fun updateExercises(selected: Boolean, exercise: Exercise) {
        if (selected) {
            selectedExercises.add(exercise)
        } else {
            selectedExercises.remove(exercise)
        }
    }

    companion object {
        const val DEFAULT_LESSON_NAME = "訓練內容"
        const val DEFAULT_LESSON_START_TIME = "18:00"
    }
}