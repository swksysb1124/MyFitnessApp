package studio.jasonsu.myfitness

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import studio.jasonsu.myfitness.app.MyFitnessApp
import studio.jasonsu.myfitness.database.AppDatabase
import studio.jasonsu.myfitness.database.exercise.ExerciseDao
import studio.jasonsu.myfitness.database.lesson.LessonDao
import studio.jasonsu.myfitness.database.profile.ProfileDao
import studio.jasonsu.myfitness.datasource.DatabaseLessonDataSource
import studio.jasonsu.myfitness.datasource.LocalLessonDataSource
import studio.jasonsu.myfitness.repository.LessonAlarmRepository
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.PersistProfileRepository
import studio.jasonsu.myfitness.repository.ProfileRepository
import studio.jasonsu.myfitness.ui.screen.main.MainViewModel
import studio.jasonsu.myfitness.util.NotificationUtil
import studio.jasonsu.myfitness.util.tts.TextToSpeakUtil

class MainActivity : ComponentActivity() {
    // data source
    private lateinit var textToSpeech: TextToSpeakUtil
    private lateinit var appDatabase: AppDatabase
    private lateinit var lessonDao: LessonDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var profileDao: ProfileDao
    private lateinit var dataSource: LocalLessonDataSource

    // repository
    private val lessonExerciseRepository: LessonExerciseRepository by lazy { LessonExerciseRepository(dataSource) }
    private val lessonRepository: LessonRepository by lazy { LessonRepository(dataSource) }
    private lateinit var profileRepository: ProfileRepository
    private val lessonAlarmRepository = LessonAlarmRepository()

    // main view model for sharing state between screen
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        initializeDataSource()
        profileRepository = PersistProfileRepository(profileDao)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.isReady.observe(this) { isReady ->
            splashScreen.setKeepOnScreenCondition { !isReady }
        }

        setLessonNotificationChannel(this)
        lessonAlarmRepository.setAlarm(this)
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

    private fun setLessonNotificationChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        NotificationUtil.createNotificationChannel(
            manager = notificationManager,
            channelId = NotificationUtil.LESSON_ALARM_CHANNEL_ID,
            channelName = NotificationUtil.LESSON_START_CHANNEL_NAME
        )
    }

    private fun initializeDataSource() {
        textToSpeech = TextToSpeakUtil.create(application)
        appDatabase = Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "my-lesson-database"
        ).build()
        lessonDao = appDatabase.lessonDao()
        exerciseDao = appDatabase.exerciseDao()
        profileDao = appDatabase.profileDao()
        dataSource = DatabaseLessonDataSource(lessonDao, exerciseDao)
    }
}
