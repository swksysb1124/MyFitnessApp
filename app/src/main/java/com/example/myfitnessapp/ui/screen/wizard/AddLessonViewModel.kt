package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.ExerciseType
import com.example.myfitnessapp.model.Time

class AddLessonViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _startTime = MutableLiveData<Time>()
    val startTime: LiveData<Time> = _startTime

    private val _weekDescription = MutableLiveData<String>()
    val weekDescription: LiveData<String> = _weekDescription

    private val _hasExerciseSelected = MutableLiveData<Boolean>()
    val hasExerciseSelected: LiveData<Boolean> = _hasExerciseSelected

    val selectedDaysOfWeek = mutableStateListOf<DayOfWeek>()

    val selectedExercises = mutableStateListOf<ExerciseType>()

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateStartTime(startTime: Time) {
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

    fun isExerciseSelected(exercise: ExerciseType): Boolean {
        return selectedExercises.contains(exercise)
    }

    fun updateExercises(selected: Boolean, exercise: ExerciseType) {
        if (selected) {
            selectedExercises.add(exercise)
        } else {
            selectedExercises.remove(exercise)
        }
        _hasExerciseSelected.value = selectedExercises.isNotEmpty()
    }

    companion object {
        const val DEFAULT_LESSON_NAME = "訓練內容"
        const val DEFAULT_LESSON_START_TIME = "18:00"
    }
}