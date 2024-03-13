package com.example.myfitnessapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: String,
    val name: String,
    val period: Int
)