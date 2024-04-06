package studio.jasonsu.myfitness

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import studio.jasonsu.myfitness.app.MyFitnessApp
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel
import studio.jasonsu.myfitness.util.NotificationUtil

class MainActivity : MyFitnessActivity() {
    // main view model for sharing state between screen
    private lateinit var mainViewModel: MainViewModel

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: intent=$intent")
        if (intent?.action == LessonAlarmBroadcastReceiver.FOREGROUND_LESSON_ALARM_ACTION) {
            val lessonAlarm = getLessonAlarm(intent)
            if (lessonAlarm == null) {
                Log.e(TAG, "received lessonAlarm is null")
                return
            }
            Log.d(TAG, "${lessonAlarm.lessonName} 開始囉!!")
            Toast.makeText(this, "${lessonAlarm.lessonName} 開始囉!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.isReady.observe(this) { isReady ->
            splashScreen.setKeepOnScreenCondition { !isReady }
        }

        setLessonNotificationChannel(this)
        setContent {
            MyFitnessApp(
                profileRepository = profileRepository,
                lessonRepository = lessonRepository,
                lessonExerciseRepository = lessonExerciseRepository,
                lessonAlarmRepository = lessonAlarmRepository,
                textToSpeech = textToSpeech,
                mainViewModel = mainViewModel,
                onFinished = ::finish
            )
        }
    }

    private fun setLessonNotificationChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationUtil.createNotificationChannel(
            manager = notificationManager,
            channelId = NotificationUtil.LESSON_ALARM_CHANNEL_ID,
            channelName = NotificationUtil.LESSON_ALARM_CHANNEL_NAME
        )
    }

    private fun getLessonAlarm(intent: Intent) =
        intent.extras?.getParcelable<LessonAlarm>(LessonAlarmBroadcastReceiver.LESSON_ALARM_EXTRA_KEY)

    companion object {
        const val TAG = "MainActivity"
    }
}
