package com.example.myfitnessapp.repository

import com.example.myfitnessapp.datasource.LocalLessonDataSource
import com.example.myfitnessapp.model.Lesson

class LessonRepository(
    private val dataSource: LocalLessonDataSource
) {
    suspend fun getLessons(): List<Lesson> = dataSource.getAllLessons()

    suspend fun getLesson(lessonId: String?): Lesson? = dataSource.getLesson(lessonId)
}