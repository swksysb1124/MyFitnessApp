package com.example.myfitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        LessonEntity::class,
        ExerciseEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun profileDao(): ProfileDao
}