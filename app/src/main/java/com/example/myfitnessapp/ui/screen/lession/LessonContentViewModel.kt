package com.example.myfitnessapp.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository

class LessonContentViewModel(
    val id: String?,
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository()
) : ViewModel() {
    private val _exercises = MutableLiveData<List<Activity<Exercise>>>()
    val exercises: LiveData<List<Activity<Exercise>>> = _exercises

    private val _buttonLabel = MutableLiveData("開始")
    val buttonLabel: LiveData<String> = _buttonLabel

    init {
        _exercises.value = lessonExerciseRepository.getActivities(id)
    }
}