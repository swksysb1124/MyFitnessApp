package com.example.myfitnessapp.database

import androidx.room.Entity

@Entity(tableName = "lesson_exercise")
data class LessonExerciseEntity(
    val lessonId: Int,
    val exerciseId: Int
)