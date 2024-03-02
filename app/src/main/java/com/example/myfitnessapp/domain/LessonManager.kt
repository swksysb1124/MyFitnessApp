package com.example.myfitnessapp.domain

import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class LessonManager(
    private val repository: LessonExerciseRepository,
    val onLessonStart: () -> Unit = {},
    val onLessonFinished: () -> Unit = {},
    val onCurrentExerciseChange: (Int, Exercise) -> Unit = { _, _ -> },
    val onTimeLeft: (Long) -> Unit = {}
) {
    val exercises: List<Exercise> = repository.getExercises()
    private val internalExercises = mutableListOf<Exercise>()
    private var currentExerciseIndex = 0

    init {
        initInternalExercises(
            exercises = exercises,
            rest = Exercise("休息時間", TimeUnit.SECONDS.toMillis(10))
        )
    }

    suspend fun startLesson() {
        onLessonStart()
        startExercise()
    }

    private suspend fun startExercise() {
        val currentExercise = getCurrentExercise()
        onCurrentExerciseChange(currentExerciseIndex, currentExercise)
        startTimer(currentExercise.duration) { timeLeftInMs ->
            onTimeLeft(timeLeftInMs)
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
        duration: Long,
        onTimeLeft: (Long) -> Unit = {}
    ) {
        // countdown timer
        var timeLeft = duration
        onTimeLeft(timeLeft)

        while (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1000
            onTimeLeft(timeLeft)
        }
    }

    private fun initInternalExercises(
        exercises: List<Exercise>,
        rest: Exercise
    ) {
        internalExercises.clear()
        internalExercises.addAll(repository.createInternalExercises(exercises, rest))
    }

    fun getCurrentExercise(): Exercise = internalExercises[currentExerciseIndex]

    fun hasNext(): Boolean =
        currentExerciseIndex < internalExercises.size - 1

    fun next() {
        if (hasNext()) {
            currentExerciseIndex += 1
        }
    }

    fun reset() {
        currentExerciseIndex = 0
    }
}