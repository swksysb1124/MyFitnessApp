package studio.jasonsu.myfitness.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.repository.LessonAlarmRepository.Companion.LESSON_ALARM_EXTRA_KEY
import studio.jasonsu.myfitness.util.NotificationUtil
import studio.jasonsu.myfitness.util.NotificationUtil.LESSON_ALARM_NOTIFICATION_ID
import java.util.Calendar

class LessonAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 顯示通知
        val lessonAlarm =
            intent.extras?.getParcelable<LessonAlarm>(LESSON_ALARM_EXTRA_KEY) ?: return
        Log.d("MyFitnessApp", "LessonAlarmBroadcastReceiver onReceive: lessonAlarm=$lessonAlarm")
        if (hasExercisesToday(lessonAlarm)) {
            Log.i("MyFitnessApp", "LessonAlarmBroadcastReceiver onReceive: has exercises today")
            sendNotification(context, lessonAlarm)
        }
        val alarmRepository = LessonAlarmRepository(context)
        alarmRepository.setLessonAlarm(lessonAlarm)
    }

    private fun hasExercisesToday(lessonAlarm: LessonAlarm): Boolean {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
        }
        val today = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> DayOfWeek.SUN
            Calendar.MONDAY -> DayOfWeek.MON
            Calendar.TUESDAY -> DayOfWeek.TUE
            Calendar.WEDNESDAY -> DayOfWeek.WED
            Calendar.THURSDAY -> DayOfWeek.THU
            Calendar.FRIDAY -> DayOfWeek.FRI
            Calendar.SATURDAY -> DayOfWeek.SAT
            else -> return false
        }
        return lessonAlarm.daysOfWeek.contains(today)
    }

    private fun sendNotification(
        context: Context,
        lessonAlarm: LessonAlarm?
    ) {
        val notification = NotificationUtil.createLessonStartNotification(context, lessonAlarm)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(LESSON_ALARM_NOTIFICATION_ID, notification)
    }
}