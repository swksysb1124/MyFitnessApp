package studio.jasonsu.myfitness.repository

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver.Companion.LESSON_ALARM_ACTION
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver.Companion.LESSON_ALARM_EXTRA_KEY
import studio.jasonsu.myfitness.model.Lesson
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.model.getTriggerAtMilli
import studio.jasonsu.myfitness.util.AlarmUtil
import java.lang.ref.WeakReference

class LessonAlarmRepository(
    context: Context
) {
    private val context = WeakReference(context)

    fun setLessonAlarm(lesson: Lesson) {
        val lessonAlarm = LessonAlarm.from(lesson)
        setLessonAlarm(lessonAlarm)
    }

    fun setLessonAlarm(lessonAlarm: LessonAlarm) {
        val context = context.get() ?: return
        val triggerAtMillis = lessonAlarm.getTriggerAtMilli()
        val pendingIntent = createLessonAlarmPendingIntent(context, lessonAlarm)
        AlarmUtil.setAlarmClock(context, triggerAtMillis, pendingIntent)
    }

    fun cancelLessonAlarm(lesson: Lesson) {
        val context = context.get() ?: return
        val lessonAlarm = LessonAlarm.from(lesson)
        Log.d("MyFitnessApp", "$TAG cancelLessonAlarm: lesson=$lesson, lessonAlarm=$lessonAlarm")
        val pendingIntent = createLessonAlarmPendingIntent(context, lessonAlarm)
        AlarmUtil.cancelAlarm(context, pendingIntent)
    }

    private fun createLessonAlarmPendingIntent(
        context: Context,
        lessonAlarm: LessonAlarm
    ): PendingIntent {
        val broadcastIntent = Intent(context, LessonAlarmBroadcastReceiver::class.java).apply {
            putExtra(LESSON_ALARM_EXTRA_KEY, lessonAlarm)
            action = LESSON_ALARM_ACTION
        }
        return PendingIntent.getBroadcast( // 當 alarm 發現會執行發送 broadcast
            context,
            lessonAlarm.lessonId?.toIntOrNull() ?: 0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val TAG = "LessonAlarmRepository"
    }
}