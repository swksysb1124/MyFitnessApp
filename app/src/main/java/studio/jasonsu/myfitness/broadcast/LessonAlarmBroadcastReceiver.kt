package studio.jasonsu.myfitness.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import studio.jasonsu.myfitness.MainActivity
import studio.jasonsu.myfitness.MyFitnessApplication
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.util.NotificationUtil
import java.util.Calendar

class LessonAlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: intent=$intent")
        val lessonAlarm = getLessonAlarm(intent)
        if (lessonAlarm == null) {
            Log.e(TAG, "lessonAlarm is null")
            return
        }
        Log.d(TAG, "onReceive: lessonAlarm=$lessonAlarm")
        if (hasExercisesToday(lessonAlarm)) {
            if (isApplicationForeground(context)) {
                notifyForegroundApplication(context, lessonAlarm)
            } else {
                NotificationUtil.sendLessonAlarmNotification(context, lessonAlarm)
            }
        }
        setNextAlarmClock(context, lessonAlarm)
    }

    private fun notifyForegroundApplication(
        context: Context,
        lessonAlarm: LessonAlarm
    ) {
        val foregroundIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(LESSON_ALARM_EXTRA_KEY, lessonAlarm)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            action = FOREGROUND_LESSON_ALARM_ACTION
        }
        context.startActivity(foregroundIntent)
    }

    private fun getLessonAlarm(intent: Intent) =
        intent.extras?.getParcelable<LessonAlarm>(LESSON_ALARM_EXTRA_KEY)

    private fun isApplicationForeground(context: Context) =
        (context.applicationContext as MyFitnessApplication).isApplicationForeground

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
        return lessonAlarm.daysOfWeek.isNotEmpty() &&
                lessonAlarm.daysOfWeek.contains(today)
    }

    private fun setNextAlarmClock(
        context: Context,
        lessonAlarm: LessonAlarm
    ) {
        val alarmRepository = LessonAlarmRepository(context)
        alarmRepository.setLessonAlarm(lessonAlarm)
    }

    companion object {
        const val TAG = "LessonAlarmBroadcastReceiver"
        const val LESSON_ALARM_EXTRA_KEY = "lesson.alarm.extra.key"
        const val LESSON_ALARM_ACTION = "studio.jasonsu.myfitness.lesson.alarm.ACTION"
        const val FOREGROUND_LESSON_ALARM_ACTION =
            "studio.jasonsu.myfitness.foreground.lesson.alarm.ACTION"
    }
}