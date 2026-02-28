package studio.jasonsu.myfitness

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isReady.collect { isReady ->
                    splashScreen.setKeepOnScreenCondition { !isReady }
                }
            }
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
