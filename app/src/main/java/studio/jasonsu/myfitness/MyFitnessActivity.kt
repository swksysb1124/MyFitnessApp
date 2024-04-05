package studio.jasonsu.myfitness

import androidx.activity.ComponentActivity
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.ProfileRepository
import studio.jasonsu.myfitness.util.tts.TextToSpeechEngine

abstract class MyFitnessActivity : ComponentActivity() {
    val profileRepository: ProfileRepository
        get() = (application as MyFitnessApplication).profileRepository
    val lessonRepository: LessonRepository
        get() = (application as MyFitnessApplication).lessonRepository

    val lessonExerciseRepository: LessonExerciseRepository
        get() = (application as MyFitnessApplication).lessonExerciseRepository

    val lessonAlarmRepository: LessonAlarmRepository
        get() = (application as MyFitnessApplication).lessonAlarmRepository

    val textToSpeech: TextToSpeechEngine
        get() = (application as MyFitnessApplication).textToSpeech

    override fun onResume() {
        super.onResume()
        enterForegroundOrNot(true)
    }

    override fun onPause() {
        super.onPause()
        enterForegroundOrNot(false)
    }

    private fun enterForegroundOrNot(entered: Boolean) {
        (application as MyFitnessApplication).isApplicationForeground = entered
    }
}