package com.example.myfitnessapp.model

enum class DayOfWeek(val value: String) {
    SUN("日"),
    MON("一"),
    TUE("二"),
    WED("三"),
    THU("四"),
    FRI("五"),
    SAT("六");

    companion object {
        private val Weekdays = listOf(MON, TUE, WED, THU, FRI)
        private val Weekend = listOf(SAT, SUN)
        val All = listOf(SUN, MON, TUE, WED, THU, FRI, SAT)
        const val Unspecified = "未指定"

        fun generateWeekDescription(daysOfWeek: List<DayOfWeek>): String {
            val size = daysOfWeek.size
            if (size == 0) {
                return Unspecified
            }
            if (size == 2 && daysOfWeek.containsAll(DayOfWeek.Weekend)) {
                return "週末"
            }
            if (size == 5 && daysOfWeek.containsAll(DayOfWeek.Weekdays)) {
                return "週間"
            }
            if (size == 7 && daysOfWeek.containsAll(DayOfWeek.All)) {
                return "每天"
            }
            val daysOfWeekDes = daysOfWeek.toList()
                .sortedBy { it.ordinal }
                .joinToString("、") {
                    if (size < 4) {
                        "週${it.value}"
                    } else {
                        it.value
                    }
                }
            return daysOfWeekDes
        }
    }
}