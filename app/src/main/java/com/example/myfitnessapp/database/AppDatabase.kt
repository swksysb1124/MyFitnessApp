package com.example.myfitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LessonEntity::class,
        ExerciseEntity::class,
        LessonExerciseEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lessonDao(): LessonDao
}