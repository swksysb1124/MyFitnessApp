package com.example.myfitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfitnessapp.database.exercise.ExerciseDao
import com.example.myfitnessapp.database.exercise.ExerciseEntity
import com.example.myfitnessapp.database.lesson.LessonDao
import com.example.myfitnessapp.database.lesson.LessonEntity
import com.example.myfitnessapp.database.profile.ProfileDao
import com.example.myfitnessapp.database.profile.ProfileEntity

@Database(
    entities = [
        LessonEntity::class,
        ExerciseEntity::class,
        ProfileEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonDao(): LessonDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun profileDao(): ProfileDao
}