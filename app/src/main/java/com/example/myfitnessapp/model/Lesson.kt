package com.example.myfitnessapp.model

data class Lesson(
    val id: String,
    val name: String? = null,
    val startTime: String = "00:00",
    val duration: Int = 0,
    val daysOfWeek: Set<DayOfWeek> = emptySet()
)

enum class DayOfWeek(val value: String) {
    SUN("日"),
    MON("一"),
    TUE("二"),
    WED("三"),
    THU("四"),
    FRI("五"),
    SAT("六");

    companion object {
        val Weekdays = listOf(MON, TUE, WED, THU, FRI)
        val Weekend = listOf(SAT, SUN)
        val All = listOf(SUN, MON, TUE, WED, THU, FRI, SAT)
    }
}