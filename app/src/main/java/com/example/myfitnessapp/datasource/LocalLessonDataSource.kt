package com.example.myfitnessapp.datasource

import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.Lesson

interface LocalLessonDataSource {
    suspend fun getAllLessons(): List<Lesson>
    suspend fun getLesson(lessonId: String?): Lesson?
    suspend fun getExercises(lessonId: String?): List<Exercise>
    suspend fun createLesson(lesson: Lesson)
    suspend fun deleteLessonById(lessonId: String)
    suspend fun createExercise(exercise: Exercise)
    suspend fun deleteExerciseById(exerciseId: String)
}