package com.example.myfitnessapp.datasource

import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.model.ExerciseType
import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.model.Time

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
            ),
            Lesson(
                id = "3",
                startTime = Time(10,0),
                duration = 30 * 60,
                daysOfWeek = listOf(DayOfWeek.SUN)
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

            "3" -> Lesson(
                id = "3",
                startTime = Time(10,0),
                duration = 30 * 60,
                daysOfWeek = listOf(DayOfWeek.SUN)
            )

            else -> null
        }
    }

    override suspend fun getExercises(lessonId: String?): List<Exercise> {
        return when (lessonId) {
            "1" -> listOf(
                Exercise.create(ExerciseType.Crunch, 30),
                Exercise.create(ExerciseType.Swimming, 60),
                Exercise.create(ExerciseType.Plank, 45),
                Exercise.create(ExerciseType.Squat, 30),
            )

            "2" -> listOf(
                Exercise.create(ExerciseType.PushUp, 30),
                Exercise.create(ExerciseType.PullUp, 60),
                Exercise.create(ExerciseType.Bridge, 60),
            )

            "3" -> listOf(
                Exercise.create(ExerciseType.MediumStrengthRunning, 30 * 60),
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

    override suspend fun createExercise(exercises: List<Exercise>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExerciseById(exerciseId: String) {
        TODO("Not yet implemented")
    }
}