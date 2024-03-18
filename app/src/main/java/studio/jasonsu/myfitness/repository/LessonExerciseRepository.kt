package studio.jasonsu.myfitness.repository

import studio.jasonsu.myfitness.datasource.LocalLessonDataSource
import studio.jasonsu.myfitness.model.Exercise

class LessonExerciseRepository(
    private val dataSource: LocalLessonDataSource
) {
    suspend fun getActivities(lessonId: String?): List<Exercise> =
        dataSource.getExercises(lessonId)

    suspend fun createLessonExercises(exercises: List<Exercise>) {
        dataSource.createExercise(exercises)
    }
}