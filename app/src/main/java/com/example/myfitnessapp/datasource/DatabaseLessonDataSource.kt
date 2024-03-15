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
import com.example.myfitnessapp.serialization.serializeDaysOfWeek

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

    override suspend fun createLesson(lesson: Lesson): Long {
        return lessonDao.insertLesson(lesson.toEntity())
    }

    override suspend fun deleteLessonById(lessonId: String) {
        lessonDao.deleteLessonById(lessonId.toInt())
    }

    override suspend fun deleteLessonsByIds(lessonIds: List<String>) {
        lessonDao.deleteLessonsByIds(lessonIds.map { it.toInt() })
    }

    override suspend fun createExercise(exercises: List<Exercise>) {
        val data = exercises.map { it.toEntity() }.toTypedArray()
        exerciseDao.insertExercises(*data)
    }

    override suspend fun deleteExerciseById(exerciseId: String) {
        TODO("Not yet implemented")
    }

    private fun Lesson.toEntity(): LessonEntity {
        return LessonEntity(
            id = id?.toIntOrNull(),
            name = name ?: "",
            startHour = startTime.hour,
            startMinute = startTime.minute,
            duration = duration,
            daysOfWeek = daysOfWeek.serializeDaysOfWeek()
        )
    }

    private fun LessonEntity.toLesson(): Lesson {
        return Lesson(
            id = id.toString(),
            name = name,
            startTime = Time(startHour, startMinute),
            duration = duration,
            daysOfWeek = daysOfWeek.deserializeDaysOfWeek()
        )
    }

    private fun Exercise.toEntity(): ExerciseEntity {
        return ExerciseEntity(
            type = type.key,
            name = type.name,
            duration = durationInSecond,
            lessonId = lessonId?.toInt()
        )
    }

    private fun ExerciseEntity.toExercise(): Exercise? {
        val type = ExerciseType.find(type) ?: return null
        return Exercise.create(type, duration)
    }
}