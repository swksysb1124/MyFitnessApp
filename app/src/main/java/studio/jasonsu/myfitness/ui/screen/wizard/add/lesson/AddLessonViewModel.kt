package studio.jasonsu.myfitness.ui.screen.wizard.add.lesson

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.model.Time
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import kotlinx.coroutines.launch

class AddLessonViewModel(
    private val lessonRepository: LessonRepository,
    private val lessonExerciseRepository: LessonExerciseRepository
) : ViewModel() {
    private val _name = MutableStateFlow(DEFAULT_LESSON_NAME)
    val name = _name.asStateFlow()

    private val _description = MutableSharedFlow<String>()
    val description = _description.asSharedFlow()

    private val _startTime = MutableStateFlow(Lesson.DefaultStartTime)
    val startTime = _startTime.asStateFlow()

    private val _weekDescription = MutableStateFlow(DayOfWeek.Unspecified)
    val weekDescription = _weekDescription.asStateFlow()

    private val _hasExerciseSelected = MutableStateFlow(false)
    val hasExerciseSelected = _hasExerciseSelected.asStateFlow()

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

    fun saveLesson(onComplete: () -> Unit) {
        val exercises = selectedExercises.map { metaData ->
            Exercise.create(metaData, metaData.suggestedDurationInSecond)
        }
        val lesson = Lesson(
            name = name.value,
            startTime = startTime.value ?: Lesson.DefaultStartTime,
            duration = exercises.fold(0) { acc, exercise -> acc + exercise.durationInSecond },
            daysOfWeek = selectedDaysOfWeek
        )
        viewModelScope.launch {
            // create a new lesson and obtain the lesson id
            val lessonId = lessonRepository.createLesson(lesson)

            // update lessonId for exercise
            val updatedExercises = exercises.map {
                Exercise.create(it.metaData, it.durationInSecond, lessonId = lessonId)
            }

            // create exercises for the given lesson
            lessonExerciseRepository.createLessonExercises(updatedExercises)

            onComplete()
        }
    }

    companion object {
        const val DEFAULT_LESSON_NAME = "訓練內容"
    }
}