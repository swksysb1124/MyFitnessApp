package studio.jasonsu.myfitness

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import studio.jasonsu.myfitness.app.MyFitnessApp
import studio.jasonsu.myfitness.autoupdate.InAppUpdateHelper
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver.Companion.FOREGROUND_LESSON_ALARM_ACTION
import studio.jasonsu.myfitness.broadcast.LessonAlarmBroadcastReceiver.Companion.LESSON_ALARM_EXTRA_KEY
import studio.jasonsu.myfitness.model.LessonAlarm
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel
import studio.jasonsu.myfitness.util.NotificationUtil
import studio.jasonsu.myfitness.util.PermissionUtil
import studio.jasonsu.myfitness.util.parcelable

class MainActivity : MyFitnessActivity() {
    // main view model for sharing state between screen
    private lateinit var mainViewModel: MainViewModel

    private val permissionUtil = PermissionUtil(
        this,
        onNotificationRequestCallback = {

        }
    )

    private val inAppUpdateHelper by lazy { InAppUpdateHelper(this, lifecycleScope) }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent: intent=$intent")
        when (intent?.action) {
            FOREGROUND_LESSON_ALARM_ACTION -> handleForegroundLessonAlarm(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        inAppUpdateHelper.onCreate()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.isReady.observe(this) { isReady ->
            splashScreen.setKeepOnScreenCondition { !isReady }
            permissionUtil.requestNotificationPermission()
        }

        NotificationUtil.setLessonNotificationChannel(this)
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

    override fun onResume() {
        super.onResume()
        inAppUpdateHelper.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        inAppUpdateHelper.onDestroy()
    }

    private fun handleForegroundLessonAlarm(intent: Intent) {
        val lessonAlarm = getLessonAlarm(intent)
        if (lessonAlarm == null) {
            Log.e(TAG, "received lessonAlarm is null")
            return
        }
        Log.d(TAG, "${lessonAlarm.lessonName} 開始囉!!")
        Toast.makeText(this, "${lessonAlarm.lessonName} 開始囉!!", Toast.LENGTH_LONG).show()
    }

    private fun getLessonAlarm(intent: Intent) =
        intent.extras?.parcelable<LessonAlarm>(LESSON_ALARM_EXTRA_KEY)

    companion object {
        const val TAG = "MainActivity"
    }
}
