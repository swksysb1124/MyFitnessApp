package com.example.myfitnessapp.ui.screen.lession.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.WeekDay

class AddLessonViewModel: ViewModel() {
    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String> = _startTime

    private val _daysOfWeek = MutableLiveData<Set<WeekDay>>()
    val daysOfWeek: LiveData<Set<WeekDay>> = _daysOfWeek

    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateStartTime(startTime: String) {
        _startTime.value = startTime
    }

    fun updateDaysOfWeek(daysOfWeek: Set<WeekDay>) {
        _daysOfWeek.value = daysOfWeek
    }

    fun updateExercises(exercises: List<Exercise>) {
        _exercises.value = exercises
    }
}