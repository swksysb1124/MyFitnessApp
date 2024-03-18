package studio.jasonsu.myfitness.domain

import kotlinx.coroutines.delay
import studio.jasonsu.myfitness.model.Activity
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.Rest

class LessonManager(
    activities: List<Exercise>,
    val onLessonStart: () -> Unit = {},
    val onLessonStop: () -> Unit = {},
    val onLessonFinished: () -> Unit = {},
    val onActivityChange: (index: Int, activity: Activity) -> Unit = { _, _ -> },
    val onActivityTimeLeft: (timeLeftInSecond: Int, activity: Activity) -> Unit = { _, _ -> }
) {
    private val internalExercises = mutableListOf<Activity>()
    private var currentExerciseIndex = 0

    @Volatile
    private var isTimerStopped = false

    init {
        initInternalExercises(
            activities = activities,
            rest = Rest
        )
    }

    suspend fun startLesson() {
        isTimerStopped = false
        onLessonStart()
        startExercise()
    }

    fun stopLesson() {
        isTimerStopped = true
    }

    private suspend fun startExercise() {
        val currentActivity = getCurrentActivity()
        onActivityChange(currentExerciseIndex, currentActivity)
        startTimer(currentActivity.durationInSecond) { timeLeftInSecond ->
            onActivityTimeLeft(timeLeftInSecond, currentActivity)
        }
        if (isTimerStopped) {
            onLessonStop()
            reset()
            return
        }
        if (hasNext()) {
            next()
            startExercise()
        } else {
            onLessonFinished()
            reset()
        }
    }

    private suspend fun startTimer(
        durationInSecond: Int,
        onTimeLeftInSecond: (Int) -> Unit = {}
    ) {
        // countdown timer
        var timeLeft = durationInSecond
        onTimeLeftInSecond(timeLeft)

        while (timeLeft > 0 && !isTimerStopped) {
            delay(1000)
            timeLeft -= 1
            onTimeLeftInSecond(timeLeft)
        }
    }

    private fun initInternalExercises(
        activities: List<Activity>,
        rest: Rest
    ) {
        internalExercises.clear()
        internalExercises.addAll(createInternalExercises(activities, rest))
    }

    private fun getCurrentActivity(): Activity = internalExercises[currentExerciseIndex]

    private fun hasNext(): Boolean =
        currentExerciseIndex < internalExercises.size - 1

    private fun next() {
        if (hasNext()) {
            currentExerciseIndex += 1
        }
    }

    private fun reset() {
        currentExerciseIndex = 0
    }

    private fun createInternalExercises(
        activities: List<Activity>,
        rest: Rest
    ): List<Activity> {
        return mutableListOf<Activity>().apply {
            activities.onEachIndexed { index, activity ->
                add(activity)
                if (index != activities.lastIndex) {
                    add(rest)
                }
            }
        }.toList()
    }
}