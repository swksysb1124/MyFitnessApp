package studio.jasonsu.myfitness.ui.screen.lession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _sumOfExerciseDuration = MutableLiveData<String>()
    val sumOfExerciseDuration: LiveData<String> = _sumOfExerciseDuration

    private val _sumOfBurnedCalories = MutableLiveData<String>()
    val sumOfBurnedCalories: LiveData<String> = _sumOfBurnedCalories

    private val _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson> = _lesson

    private val _buttonLabel = MutableLiveData("立即開始")
    val buttonLabel: LiveData<String> = _buttonLabel

    init {
        viewModelScope.launch {
            _lesson.value = lessonRepository.getLesson(id)

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