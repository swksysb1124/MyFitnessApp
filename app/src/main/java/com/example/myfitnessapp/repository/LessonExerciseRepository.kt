package com.example.myfitnessapp.repository

import com.example.myfitnessapp.R
import com.example.myfitnessapp.model.Exercise
import java.util.concurrent.TimeUnit

class LessonExerciseRepository {
    fun getExercises(): List<Exercise> {
        return listOf(
            Exercise("深蹲", TimeUnit.SECONDS.toMillis(30), R.drawable.squat),
            Exercise("伏地挺身", TimeUnit.SECONDS.toMillis(30), R.drawable.push_up),
            Exercise("平板支撐", TimeUnit.SECONDS.toMillis(60), R.drawable.plank)
        )
    }

    fun createInternalExercises(
        exercises: List<Exercise>,
        restExercise: Exercise
    ): List<Exercise> {
        return mutableListOf<Exercise>().apply {
            exercises.onEachIndexed { index, exercise ->
                add(exercise)
                if (index != exercises.lastIndex) {
                    add(restExercise)
                }
            }
        }.toList()
    }
}