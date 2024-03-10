package com.example.myfitnessapp.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfitnessapp.domain.CaloriesBurnedCalculator
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
import com.example.myfitnessapp.util.formattedDuration
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LessonContentViewModel(
    val id: String?,
    lessonRepository: LessonRepository = LessonRepository(),
    lessonExerciseRepository: LessonExerciseRepository = LessonExerciseRepository(),
    profileRepository: ProfileRepository = ProfileRepository(),
    private val calculator: CaloriesBurnedCalculator = CaloriesBurnedCalculator()
) : ViewModel() {
    private val _exercises = MutableLiveData<List<Activity<Exercise>>>()
    val exercises: LiveData<List<Activity<Exercise>>> = _exercises

    private val _sumOfExerciseDuration = MutableLiveData<String>()
    val sumOfExerciseDuration: LiveData<String> = _sumOfExerciseDuration

    private val _sumOfBurnedCalories = MutableLiveData<Int>()
    val sumOfBurnedCalories: LiveData<Int> = _sumOfBurnedCalories

    private val _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson> = _lesson

    private val _buttonLabel = MutableLiveData("立即開始")
    val buttonLabel: LiveData<String> = _buttonLabel

    init {
        viewModelScope.launch {
            _lesson.value = lessonRepository.getLesson(id)

            val fetchedExercises = lessonExerciseRepository.getActivities(id)
            val fetchedProfile = profileRepository.getProfile()

            _exercises.value = fetchedExercises

            _sumOfExerciseDuration.value =
                fetchedExercises.sumOf { it.durationInSecond }.formattedDuration()

            _sumOfBurnedCalories.value = fetchedExercises.sumOf {
                val exercise = it.content
                calculator.calculate(
                    mets = exercise.mets,
                    weightInKg = fetchedProfile.weight,
                    mins = (it.durationInSecond.toDouble() / 60.0)
                )
            }.roundToInt()
        }
    }
}