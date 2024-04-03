package studio.jasonsu.myfitness.datasource

import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.Lesson

interface LocalLessonDataSource {
    suspend fun getAllLessons(): List<Lesson>
    suspend fun getLesson(lessonId: String?): Lesson?
    suspend fun getLessonsByIds(lessonIds: List<String>):  List<Lesson>
    suspend fun getExercises(lessonId: String?): List<Exercise>
    suspend fun createLesson(lesson: Lesson): Long
    suspend fun deleteLessonById(lessonId: String)
    suspend fun deleteLessonsByIds(lessonIds: List<String>)
    suspend fun createExercise(exercises: List<Exercise>)
    suspend fun deleteExerciseById(exerciseId: String)
}