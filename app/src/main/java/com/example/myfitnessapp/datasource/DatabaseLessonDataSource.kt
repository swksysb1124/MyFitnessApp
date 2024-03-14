package com.example.myfitnessapp.datasource

import com.example.myfitnessapp.database.ExerciseDao
import com.example.myfitnessapp.database.ExerciseEntity
import com.example.myfitnessapp.database.LessonDao
import com.example.myfitnessapp.database.LessonEntity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.ExerciseType
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.model.Time
import com.example.myfitnessapp.serialization.deserializeDaysOfWeek

class DatabaseLessonDataSource(
    private val lessonDao: LessonDao,
    private val exerciseDao: ExerciseDao
) : LocalLessonDataSource {
    override suspend fun getAllLessons(): List<Lesson> {
        return lessonDao.getAllLessons()
            .map { it.toLesson() }
    }

    override suspend fun getLesson(lessonId: String?): Lesson? {
        val id = lessonId?.toIntOrNull() ?: return null
        return lessonDao.getLessonById(id).toLesson()
    }



    override suspend fun getExercises(lessonId: String?): List<Exercise> {
        val id = lessonId?.toIntOrNull() ?: return emptyList()
        return exerciseDao.getExercisesByLessonId(id)
            .mapNotNull { it.toExercise() }
    }

    override fun createLesson(lesson: Lesson) {
        TODO("Not yet implemented")
    }

    override fun deleteLessonById(lessonId: String) {
        TODO("Not yet implemented")
    }

    override fun createExercise(exercise: Exercise) {
        TODO("Not yet implemented")
    }

    override fun deleteExerciseById(exerciseId: String) {
        TODO("Not yet implemented")
    }

    private fun LessonEntity.toLesson(): Lesson {
        val daysOfWeek = daysOfWeek.deserializeDaysOfWeek()
        return Lesson(
            id = id.toString(),
            name = name,
            startTime = Time(startHour, startMinute),
            duration = duration,
            daysOfWeek = daysOfWeek
        )
    }

    private fun ExerciseEntity.toExercise(): Exercise? {
        val type = ExerciseType.find(type) ?: return null
        return Exercise.create(type, duration)
    }
}