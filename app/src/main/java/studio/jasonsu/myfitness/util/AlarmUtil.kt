package studio.jasonsu.myfitness.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context

object AlarmUtil {

    fun setRepeatingAlarm(context: Context, triggerAtMillis: Long, intervalMillis: Long, pendingIntent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, pendingIntent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}