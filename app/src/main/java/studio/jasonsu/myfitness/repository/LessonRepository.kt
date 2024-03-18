package studio.jasonsu.myfitness.repository

import studio.jasonsu.myfitness.datasource.LocalLessonDataSource
import studio.jasonsu.myfitness.model.Lesson

class LessonRepository(
    private val dataSource: LocalLessonDataSource
) {
    suspend fun getLessons(): List<Lesson> = dataSource.getAllLessons()

    suspend fun getLesson(lessonId: String?): Lesson? = dataSource.getLesson(lessonId)

    suspend fun createLesson(lesson: Lesson) = dataSource.createLesson(lesson)

    suspend fun deleteLessonsByIds(lessonIds: List<String>) = dataSource.deleteLessonsByIds(lessonIds)
}