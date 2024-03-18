package studio.jasonsu.myfitness.model

data class Lesson(
    val id: String? = null,
    val name: String? = null,
    val startTime: Time = DefaultStartTime,
    val duration: Int = 0,
    val daysOfWeek: List<DayOfWeek> = emptyList()
) {
    companion object {
        val DefaultStartTime = Time(18, 0)
    }
}
