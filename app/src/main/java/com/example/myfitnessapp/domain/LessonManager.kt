package com.example.myfitnessapp.domain

import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest
import com.example.myfitnessapp.repository.LessonExerciseRepository
import kotlinx.coroutines.delay

class LessonManager(
    private val id: String?,
    private val repository: LessonExerciseRepository,
    val onLessonStart: () -> Unit = {},
    val onLessonStop: () -> Unit = {},
    val onLessonFinished: () -> Unit = {},
    val onActivityChange: (index: Int, activity: Activity<*>) -> Unit = { _, _ -> },
    val onActivityTimeLeft: (timeLeftInSecond: Int, activity: Activity<*>) -> Unit = { _, _ -> }
) {
    val activities: List<Activity<Exercise>> = repository.getActivities(id)
    private val internalExercises = mutableListOf<Activity<*>>()
    private var currentExerciseIndex = 0

    @Volatile
    private var isTimerStopped = false

    init {
        initInternalExercises(
            activities = activities,
            rest = Activity(Rest, 10)
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
        activities: List<Activity<*>>,
        rest: Activity<Rest>
    ) {
        internalExercises.clear()
        internalExercises.addAll(repository.createInternalExercises(activities, rest))
    }

    private fun getCurrentActivity(): Activity<*> = internalExercises[currentExerciseIndex]

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
}