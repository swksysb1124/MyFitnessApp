package com.example.myfitnessapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson")
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val startHour: Int,
    val startMinute: Int,
    val daysOfWeek: String
)