package com.example.myfitnessapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LessonDao {
    @Query("SELECT * FROM lesson")
    suspend fun getAllLessons(): List<LessonEntity>

    @Query("SELECT * FROM lesson WHERE id = :lessonId")
    suspend fun getLessonById(lessonId: Int): LessonEntity

    @Query("SELECT * FROM exercise WHERE id IN (SELECT exerciseId FROM lesson_exercise WHERE lessonId = :lessonId)")
    suspend fun getExercisesByLessonId(lessonId: Int): List<ExerciseEntity>

    @Insert
    suspend fun insertLesson(lesson: LessonEntity)

    @Insert
    suspend fun insertLessonExercise(lessonExercise: LessonExerciseEntity)
}