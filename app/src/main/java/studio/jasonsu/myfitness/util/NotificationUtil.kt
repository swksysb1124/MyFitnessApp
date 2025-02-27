package studio.jasonsu.myfitness.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import studio.jasonsu.myfitness.MainActivity
import studio.jasonsu.myfitness.R
import studio.jasonsu.myfitness.model.LessonAlarm

object NotificationUtil {
    fun setLessonNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            createNotificationChannel(
                manager = notificationManager,
                channelId = LESSON_ALARM_CHANNEL_ID,
                channelName = LESSON_ALARM_CHANNEL_NAME
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        manager: NotificationManager,
        channelId: String,
        channelName: String
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        manager.createNotificationChannel(channel)
    }

    fun sendLessonAlarmNotification(
        context: Context,
        lessonAlarm: LessonAlarm?
    ) {
        val notification = createLessonAlarmNotification(context, lessonAlarm)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(LESSON_ALARM_NOTIFICATION_ID, notification)
    }

    private fun createLessonAlarmNotification(
        context: Context,
        lessonAlarm: LessonAlarm?
    ): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val contentText = when {
            lessonAlarm != null ->
                "${lessonAlarm.lessonName}將在${lessonAlarm.time.hour}:${lessonAlarm.time.minute}開始!"

            else -> "您安排的運動即將開始!"
        }

        return NotificationCompat.Builder(context, LESSON_ALARM_CHANNEL_ID)
            .setContentTitle("運動小幫手提醒")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
    }

    private const val LESSON_ALARM_CHANNEL_ID = "myfitness.notification.channel.id"
    private const val LESSON_ALARM_CHANNEL_NAME = "運動提醒"
    private const val LESSON_ALARM_NOTIFICATION_ID = 0x110
}