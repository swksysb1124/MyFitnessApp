package com.example.myfitnessapp.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import kotlinx.coroutines.launch

class LessonContentViewModel(
    val id: String?,
    lessonRepository: LessonRepository = LessonRepository(),
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository()
) : ViewModel() {
    private val _exercises = MutableLiveData<List<Activity<Exercise>>>()
    val exercises: LiveData<List<Activity<Exercise>>> = _exercises

    private val _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson> = _lesson

    private val _buttonLabel = MutableLiveData("立即開始")
    val buttonLabel: LiveData<String> = _buttonLabel

    init {
        viewModelScope.launch {
            _lesson.value = lessonRepository.getLesson(id)
            _exercises.value = lessonExerciseRepository.getActivities(id)
        }
    }
}