package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest

class LessonExerciseRepository {
    fun getActivities(): List<Activity<Exercise>> {
        return listOf(
            Activity(Exercise.Swimming, 3600),
            Activity(Exercise.PullUp, 30),
            Activity(Exercise.Squat, 30),
            Activity(Exercise.PushUp, 30),
            Activity(Exercise.Plank, 60)
        )
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