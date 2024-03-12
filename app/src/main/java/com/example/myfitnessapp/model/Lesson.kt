package com.example.myfitnessapp.model

data class Lesson(
    val id: String,
    val name: String? = null,
    val startTime: String = "00:00",
    val duration: Int = 0,
    val daysOfWeek: List<DayOfWeek> = emptyList()
)
