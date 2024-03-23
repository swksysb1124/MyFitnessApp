package studio.jasonsu.myfitness.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver

class LessonAlarmRepository {
    // TODO: set real alarm with hour, minute, days of week
    fun setAlarm(context: Context) {
        val triggerAtMillis = System.currentTimeMillis() + 20 * 1000 // 24 小時後
//        val intervalMillis = AlarmManager.INTERVAL_DAY * 7 // 7天一週期
        val intent = Intent(context, LessonAlarmBroadcastReceiver::class.java).apply {
            action = "MY_ACTION"
        }
        val pendingIntent = PendingIntent.getBroadcast( // 當 alarm 發現會執行發送 broadcast
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }
}