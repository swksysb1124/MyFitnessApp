package com.example.myfitnessapp.repository

import com.example.myfitnessapp.model.Lesson
import com.example.myfitnessapp.model.DayOfWeek

class LessonRepository {
    fun getLessons(): List<Lesson> {
        return listOf(
            Lesson(
                name = "晨間運動",
                id = "1",
                startTime = "07:00",
                duration = 165,
                daysOfWeek = setOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.WED)
            ),
            Lesson(
                name = "夜間運動",
                id = "2",
                startTime = "18:00",
                duration = 150,
                daysOfWeek = setOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.FRI, DayOfWeek.SAT)
            ),
            Lesson(
                id = "3",
                startTime = "10:00",
                duration = 30 * 60,
                daysOfWeek = setOf(DayOfWeek.SUN)
            )
        )
    }

    fun getLesson(lessonId: String?): Lesson? {
        return when (lessonId) {
            "1" -> Lesson(
                name = "晨間運動",
                id = "1",
                startTime = "07:00",
                duration = 165,
                daysOfWeek = setOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.WED)
            )

            "2" -> Lesson(
                name = "夜間運動",
                id = "2",
                startTime = "18:00",
                duration = 150,
                daysOfWeek = setOf(DayOfWeek.MON, DayOfWeek.THU, DayOfWeek.FRI, DayOfWeek.SAT)
            )

            "3" -> Lesson(
                id = "3",
                startTime = "10:00",
                duration = 30 * 60,
                daysOfWeek = setOf(DayOfWeek.SUN)
            )

            else -> null
        }
    }
}