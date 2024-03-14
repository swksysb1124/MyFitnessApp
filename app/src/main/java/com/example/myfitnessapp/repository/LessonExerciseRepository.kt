package com.example.myfitnessapp.repository

import com.example.myfitnessapp.datasource.LocalLessonDataSource
import com.example.myfitnessapp.model.Exercise

class LessonExerciseRepository(
    private val dataSource: LocalLessonDataSource
) {
    suspend fun getActivities(lessonId: String?): List<Exercise> =
        dataSource.getExercises(lessonId)

    suspend fun createLessonExercises(exercises: List<Exercise>) {
        dataSource.createExercise(exercises)
    }
}