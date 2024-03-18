package studio.jasonsu.myfitness.datasource

import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.model.Time

class MocKLessonDataSource: LocalLessonDataSource {
    override suspend fun getAllLessons(): List<Lesson> {
        return listOf(
            Lesson(
                name = "晨間運動",
                id = "1",
                startTime = Time(7,0),
                duration = 165,
                daysOfWeek = listOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.WED)
            ),
            Lesson(
                name = "夜間運動",
                id = "2",
                startTime = Time(18,0),
                duration = 150,
                daysOfWeek = listOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.FRI, DayOfWeek.SAT)
            )
        )
    }

    override suspend fun getLesson(lessonId: String?): Lesson? {
        return when (lessonId) {
            "1" -> Lesson(
                name = "晨間運動",
                id = "1",
                startTime = Time(7,0),
                duration = 165,
                daysOfWeek = listOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.WED)
            )

            "2" -> Lesson(
                name = "夜間運動",
                id = "2",
                startTime = Time(18,0),
                duration = 150,
                daysOfWeek = listOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.FRI, DayOfWeek.SAT)
            )

            else -> null
        }
    }

    override suspend fun getExercises(lessonId: String?): List<Exercise> {
        return when (lessonId) {
            "1" -> listOf(
                Exercise.create(ExerciseMetaData.Crunch, 30),
                Exercise.create(ExerciseMetaData.Swimming, 60),
                Exercise.create(ExerciseMetaData.Plank, 45),
                Exercise.create(ExerciseMetaData.Squat, 30),
            )

            "2" -> listOf(
                Exercise.create(ExerciseMetaData.PushUp, 30),
                Exercise.create(ExerciseMetaData.PullUp, 60),
                Exercise.create(ExerciseMetaData.Bridge, 60),
            )

            else -> emptyList()
        }
    }

    override suspend fun createLesson(lesson: Lesson): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLessonById(lessonId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLessonsByIds(lessonIds: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun createExercise(exercises: List<Exercise>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExerciseById(exerciseId: String) {
        TODO("Not yet implemented")
    }
}