package com.example.myfitnessapp.serialization

import com.example.myfitnessapp.model.DayOfWeek

// format: SUN:MON:TUE:WED:THU:FRI:SAT
fun String.deserializeDaysOfWeek(): List<DayOfWeek> {
    if (isEmpty()) return emptyList()
    val days = this.split(":")
    return days.map { day ->
        DayOfWeek.entries.first { daysOfWeek -> daysOfWeek.name == day }
    }
}

// format: SUN:MON:TUE:WED:THU:FRI:SAT
fun List<DayOfWeek>.serializeDaysOfWeek(): String {
    if (isEmpty()) return ""
    return this.joinToString(":") {
        it.name
    }
}