package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.ExerciseMetaData
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.model.Time
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import kotlinx.coroutines.launch

class AddLessonViewModel(
    private val lessonRepository: LessonRepository,
    private val lessonExerciseRepository: LessonExerciseRepository
) : ViewModel() {
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _startTime = MutableLiveData<Time>()
    val startTime: LiveData<Time> = _startTime

    private val _weekDescription = MutableLiveData<String>()
    val weekDescription: LiveData<String> = _weekDescription

    private val _hasExerciseSelected = MutableLiveData<Boolean>()
    val hasExerciseSelected: LiveData<Boolean> = _hasExerciseSelected

    val selectedDaysOfWeek = mutableStateListOf<DayOfWeek>()

    val selectedExercises = mutableStateListOf<ExerciseMetaData>()

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

    fun isExerciseSelected(exercise: ExerciseMetaData): Boolean {
        return selectedExercises.contains(exercise)
    }

    fun updateExercises(selected: Boolean, exercise: ExerciseMetaData) {
        if (selected) {
            selectedExercises.add(exercise)
        } else {
            selectedExercises.remove(exercise)
        }
        _hasExerciseSelected.value = selectedExercises.isNotEmpty()
    }

    fun save() {
        val exercises = selectedExercises.map {
            Exercise.create(it, Exercise.DefaultDuration)
        }
        val lesson = Lesson(
            name = name.value,
            startTime = startTime.value ?: Lesson.DefaultStartTime,
            duration = exercises.fold(0) { acc, exercise -> acc + exercise.durationInSecond },
            daysOfWeek = selectedDaysOfWeek
        )
        viewModelScope.launch {
            val lessonId = lessonRepository.createLesson(lesson)
            val updated = exercises.map {
                Exercise.create(it.metaData, it.durationInSecond, lessonId = lessonId)
            }
            lessonExerciseRepository.createLessonExercises(updated)
        }
    }

    companion object {
        const val DEFAULT_LESSON_NAME = "訓練內容"
    }
}