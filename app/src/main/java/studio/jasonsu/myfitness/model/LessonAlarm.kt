package studio.jasonsu.myfitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import java.util.Calendar

@Parcelize
data class LessonAlarm(
    val lessonId: String?,
    val time: Time,
    val daysOfWeek: List<DayOfWeek>
) : Parcelable {
    companion object {
        fun from(lesson: Lesson): LessonAlarm =
            LessonAlarm(lesson.id, lesson.startTime, lesson.daysOfWeek)
    }
}

fun LessonAlarm.getTriggerAtMilli(): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    calendar.set(Calendar.HOUR_OF_DAY, time.hour)
    calendar.set(Calendar.MINUTE, time.minute)
    calendar.set(Calendar.SECOND, 0)
    if (isEarlyThanCurrent()) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return calendar.timeInMillis
}

private fun LessonAlarm.isEarlyThanCurrent(): Boolean {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)
    return currentHour * 60 + currentMinute > time.hour * 60 + time.minute
}
