package studio.jasonsu.myfitness.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import studio.jasonsu.myfitness.util.NotificationUtil

class LessonAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 顯示通知
        val notification = NotificationUtil.createLessonStartNotification(context)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}