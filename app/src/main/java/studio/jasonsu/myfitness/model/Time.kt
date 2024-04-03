package studio.jasonsu.myfitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 24 hour time system
 */
@Parcelize
data class Time(
    val hour: Int,
    val minute: Int,
): Parcelable {
    override fun toString(): String {
        val formattedHour = timeFormat(hour)
        val formattedMinute = timeFormat(minute)
        return "$formattedHour:$formattedMinute"
    }

    private fun timeFormat(time: Int) = when (time) {
        0 -> "00"
        in 1..9 -> "0$time"
        else -> time.toString()
    }
}


