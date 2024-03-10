package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest

class LessonExerciseRepository {
    fun getActivities(lessonId: String?): List<Activity<Exercise>> {
        return when (lessonId) {
            "1" -> listOf(
                Activity(Exercise.Crunch, 30),
                Activity(Exercise.Swimming, 60),
                Activity(Exercise.Plank, 45),
                Activity(Exercise.Squat, 30),
            )

            "2" -> listOf(
                Activity(Exercise.PushUp, 30),
                Activity(Exercise.PullUp, 60),
                Activity(Exercise.Bridge, 60),
            )

            "3" -> listOf(
                Activity(Exercise.MediumStrengthRunning, 30 * 60),
            )

            else -> emptyList()
        }
    }

    fun createInternalExercises(
        activities: List<Activity<*>>,
        rest: Activity<Rest>
    ): List<Activity<*>> {
        return mutableListOf<Activity<*>>().apply {
            activities.onEachIndexed { index, activity ->
                add(activity)
                if (index != activities.lastIndex) {
                    add(rest)
                }
            }
        }.toList()
    }
}