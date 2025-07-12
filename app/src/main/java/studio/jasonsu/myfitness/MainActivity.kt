package studio.jasonsu.myfitness

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import studio.jasonsu.myfitness.app.MyFitnessApp
import studio.jasonsu.myfitness.autoupdate.InAppUpdateHelper
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel

class MainActivity : MyFitnessActivity() {
    // main view model for sharing state between screen
    private lateinit var mainViewModel: MainViewModel

    private val inAppUpdateHelper by lazy { InAppUpdateHelper(this, lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        inAppUpdateHelper.onCreate()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.isReady.observe(this) { isReady ->
            splashScreen.setKeepOnScreenCondition { !isReady }
        }

        setContent {
            MyFitnessApp(
                profileRepository = profileRepository,
                lessonRepository = lessonRepository,
                lessonExerciseRepository = lessonExerciseRepository,
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

    companion object {
        const val TAG = "MainActivity"
    }
}
