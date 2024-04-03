package studio.jasonsu.myfitness.datasource

import studio.jasonsu.myfitness.database.exercise.ExerciseDao
import studio.jasonsu.myfitness.database.exercise.ExerciseEntity
import studio.jasonsu.myfitness.database.lesson.LessonDao
import studio.jasonsu.myfitness.database.lesson.LessonEntity
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.model.Time
import studio.jasonsu.myfitness.serialization.deserializeDaysOfWeek
import studio.jasonsu.myfitness.serialization.serializeDaysOfWeek

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

    override suspend fun getLessonsByIds(lessonIds: List<String>): List<Lesson> {
        if (lessonIds.isEmpty()) return emptyList()
        return lessonDao.getLessonsByIds(lessonIds.map { it.toInt() })
            .map { it.toLesson() }
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
            type = metaData.key,
            name = metaData.name,
            duration = durationInSecond,
            lessonId = lessonId?.toInt()
        )
    }

    private fun ExerciseEntity.toExercise(): Exercise? {
        val meta = ExerciseMetaData.find(type) ?: return null
        return Exercise.create(meta, duration)
    }
}