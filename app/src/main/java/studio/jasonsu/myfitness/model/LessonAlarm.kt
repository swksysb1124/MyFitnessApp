package studio.jasonsu.myfitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import java.util.Calendar

@Parcelize
data class LessonAlarm(
    val lessonId: String?,
    val lessonName: String?,
    val time: Time,
    val daysOfWeek: List<DayOfWeek>
) : Parcelable {
    companion object {
        fun from(lesson: Lesson): LessonAlarm =
            LessonAlarm(lesson.id, lesson.name, lesson.startTime, lesson.daysOfWeek)
    }
}

/**
 * covert the start time of the lesson alarm in millisecond
 */
fun LessonAlarm.getTriggerAtMilli(): Long {
    val currentTime = System.currentTimeMillis()

    val calendar = Calendar.getInstance().apply {
        timeInMillis = currentTime
        set(Calendar.HOUR_OF_DAY, this@getTriggerAtMilli.time.hour)
        set(Calendar.MINUTE, this@getTriggerAtMilli.time.minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    // Add one day if the start time of the lesson alarm has passed
    if (calendar.timeInMillis < currentTime) {
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }

    return calendar.timeInMillis
}
