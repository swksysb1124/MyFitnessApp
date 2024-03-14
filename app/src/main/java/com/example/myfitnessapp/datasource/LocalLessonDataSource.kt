package com.example.myfitnessapp.datasource

import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Lesson

interface LocalLessonDataSource {
    suspend fun getAllLessons(): List<Lesson>
    suspend fun getLesson(lessonId: String?): Lesson?
    suspend fun getActivities(lessonId: String?): List<Activity<Exercise>>
    fun createLesson(lesson: Lesson)
    fun deleteLessonById(lessonId: String)
    fun createExercise(exercise: Exercise)
    fun deleteExerciseById(exerciseId: String)
}