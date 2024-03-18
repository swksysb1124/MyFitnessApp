package studio.jasonsu.myfitness.serialization

import studio.jasonsu.myfitness.model.DayOfWeek

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