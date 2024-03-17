package com.example.myfitnessapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfitnessapp.model.Exercise

@Dao
interface LessonDao {
    @Insert
    suspend fun insertLesson(lesson: LessonEntity)

    @Query("SELECT * FROM lesson")
    suspend fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM lesson WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Int): LessonEntity

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Delete
    suspend fun deleteLesson(lesson: LessonEntity)
}