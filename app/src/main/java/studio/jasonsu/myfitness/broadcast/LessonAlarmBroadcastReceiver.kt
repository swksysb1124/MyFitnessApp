package studio.jasonsu.myfitness.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.repository.LessonAlarmRepository.Companion.LESSON_ALARM_EXTRA_KEY
import studio.jasonsu.myfitness.util.NotificationUtil

class LessonAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 顯示通知
        val lessonAlarm = intent.extras?.getParcelable<LessonAlarm>(LESSON_ALARM_EXTRA_KEY)
        Log.d("MyFitnessApp", "LessonAlarmBroadcastReceiver onReceive: lessonAlarm=$lessonAlarm")
        val notification = NotificationUtil.createLessonStartNotification(context, lessonAlarm)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}