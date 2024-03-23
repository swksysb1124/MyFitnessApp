package studio.jasonsu.myfitness.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import studio.jasonsu.myfitness.MainActivity
import studio.jasonsu.myfitness.R

object NotificationUtil {
    fun createNotificationChannel(
        manager: NotificationManager,
        channelId: String,
        channelName: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            manager.createNotificationChannel(channel)
        }
    }

    fun createLessonStartNotification(context: Context): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(context, LESSON_ALARM_CHANNEL_ID)
            .setContentTitle("您的運動開始了")
            .setContentText("請開始運動")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
    }

    const val LESSON_ALARM_CHANNEL_ID = "myfitness.notification.alarm.channel"
    const val LESSON_START_CHANNEL_NAME = "myfitness.notification.start.lesson"
}