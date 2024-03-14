package com.example.myfitnessapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = LessonEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("lesson_id"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("lesson_id")
    ]
)
class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "lesson_id") val lessonId: Int? = null,
)