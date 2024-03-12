package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.DayOfWeek

class AddLessonViewModel : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String> = _startTime

    private val _daysOfWeek = MutableLiveData<Set<DayOfWeek>>()
    val daysOfWeek: LiveData<Set<DayOfWeek>> = _daysOfWeek

    private val selectedExercises = mutableStateListOf<Exercise>()

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateStartTime(startTime: String) {
        _startTime.value = startTime
    }

    fun updateDaysOfWeek(daysOfWeek: Set<DayOfWeek>) {
        _daysOfWeek.value = daysOfWeek
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