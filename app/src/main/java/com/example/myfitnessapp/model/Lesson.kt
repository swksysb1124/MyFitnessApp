package com.example.myfitnessapp.model

data class Lesson(
    val id: String,
    val name: String? = null,
    val startTime: String = "00:00",
    val duration: Int = 0,
    val daysOfWeek: Set<WeekDay> = emptySet()
)

enum class WeekDay(val value: String) {
    SUN("日"),
    MON("一"),
    TUE("二"),
    WED("三"),
    THU("四"),
    FRI("五"),
    SAT("六")
}