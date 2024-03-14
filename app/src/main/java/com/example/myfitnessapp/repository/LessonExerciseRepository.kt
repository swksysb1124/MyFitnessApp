package com.example.myfitnessapp.repository

import com.example.myfitnessapp.datasource.LocalLessonDataSource
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Rest

class LessonExerciseRepository(
    private val dataSource: LocalLessonDataSource
) {
    suspend fun getActivities(lessonId: String?): List<Activity<Exercise>> =
        dataSource.getActivities(lessonId)

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