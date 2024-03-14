package com.example.myfitnessapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myfitnessapp.model.Exercise

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(vararg exercises: Exercise)

    @Query("SELECT * FROM exercise WHERE lesson_id=:lessonId")
    suspend fun getExercisesByLessonId(lessonId: Int): List<ExerciseEntity>

    @Delete
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}