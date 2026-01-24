package studio.jasonsu.myfitness.ui.screen.lession

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import studio.jasonsu.myfitness.domain.CaloriesBurnedCalculator
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.ProfileRepository
import studio.jasonsu.myfitness.util.spokenDuration
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LessonContentViewModel(
    val id: String?,
    lessonRepository: LessonRepository,
    lessonExerciseRepository: LessonExerciseRepository,
    profileRepository: ProfileRepository,
    private val calculator: CaloriesBurnedCalculator = CaloriesBurnedCalculator()
) : ViewModel() {
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises = _exercises.asStateFlow()

    private val _sumOfExerciseDuration = MutableStateFlow("")
    val sumOfExerciseDuration = _sumOfExerciseDuration.asStateFlow()

    private val _sumOfBurnedCalories = MutableStateFlow("-")
    val sumOfBurnedCalories = _sumOfBurnedCalories.asStateFlow()

    private val _lesson = MutableStateFlow(Lesson())
    val lesson = _lesson.asStateFlow()

    private val _buttonLabel = MutableStateFlow("立即開始")
    val buttonLabel = _buttonLabel.asStateFlow()

    init {
        viewModelScope.launch {
            _lesson.value = lessonRepository.getLesson(id) ?: Lesson()

            val fetchedExercises = lessonExerciseRepository.getActivities(id)
            _exercises.value = fetchedExercises

            _sumOfExerciseDuration.value =
                fetchedExercises.sumOf { it.durationInSecond }.spokenDuration()

            val fetchedProfile = profileRepository.getProfile() ?: return@launch
            _sumOfBurnedCalories.value = fetchedExercises.sumOf {
                calculator.calculate(
                    mets = it.metaData.mets,
                    weightInKg = fetchedProfile.weight.toDouble(),
                    mins = (it.durationInSecond.toDouble() / 60.0)
                )
            }.roundToInt().let { "${it}千卡" }
        }
    }
}