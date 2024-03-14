package com.example.myfitnessapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(vararg exercises: ExerciseEntity)

    @Query("SELECT * FROM exercise WHERE lesson_id=:lessonId")
    suspend fun getExercisesByLessonId(lessonId: Int): List<ExerciseEntity>

    @Delete
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
}