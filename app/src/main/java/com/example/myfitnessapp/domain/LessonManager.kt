package com.example.myfitnessapp.domain

import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.repository.LessonExerciseRepository
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class LessonManager(
    private val repository: LessonExerciseRepository,
    val onLessonStart: () -> Unit = {},
    val onLessonFinished: () -> Unit = {},
    val onExerciseChange: (index: Int, exercise: Exercise) -> Unit = { _, _ -> },
    val onExerciseTimeLeft: (timeLeftIsMs: Long, exercise: Exercise) -> Unit = { _, _ -> }
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
        onExerciseChange(currentExerciseIndex, currentExercise)
        startTimer(currentExercise.duration) { timeLeftInMs ->
            onExerciseTimeLeft(timeLeftInMs, currentExercise)
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

    private fun getCurrentExercise(): Exercise = internalExercises[currentExerciseIndex]

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