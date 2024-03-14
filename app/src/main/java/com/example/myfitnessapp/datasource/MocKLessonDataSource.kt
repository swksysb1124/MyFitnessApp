package com.example.myfitnessapp.datasource

import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Exercise
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

    override suspend fun getActivities(lessonId: String?): List<Activity<Exercise>> {
        return when (lessonId) {
            "1" -> listOf(
                Activity(Exercise.Crunch, 30),
                Activity(Exercise.Swimming, 60),
                Activity(Exercise.Plank, 45),
                Activity(Exercise.Squat, 30),
            )

            "2" -> listOf(
                Activity(Exercise.PushUp, 30),
                Activity(Exercise.PullUp, 60),
                Activity(Exercise.Bridge, 60),
            )

            "3" -> listOf(
                Activity(Exercise.MediumStrengthRunning, 30 * 60),
            )

            else -> emptyList()
        }
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
}