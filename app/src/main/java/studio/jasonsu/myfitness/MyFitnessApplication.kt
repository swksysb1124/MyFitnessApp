package studio.jasonsu.myfitness

import android.app.Application
import androidx.room.Room
import studio.jasonsu.myfitness.database.AppDatabase
import studio.jasonsu.myfitness.database.exercise.ExerciseDao
import studio.jasonsu.myfitness.database.lesson.LessonDao
import studio.jasonsu.myfitness.database.profile.ProfileDao
import studio.jasonsu.myfitness.datasource.DatabaseLessonDataSource
import studio.jasonsu.myfitness.datasource.LocalLessonDataSource
import studio.jasonsu.myfitness.repository.LessonExerciseRepository
import studio.jasonsu.myfitness.repository.LessonRepository
import studio.jasonsu.myfitness.repository.PersistProfileRepository
import studio.jasonsu.myfitness.repository.ProfileRepository
import studio.jasonsu.myfitness.util.tts.TextToSpeakUtil
import studio.jasonsu.myfitness.util.tts.TextToSpeechEngine

class MyFitnessApplication: Application() {
    var isApplicationForeground = false

    // data source
    lateinit var textToSpeech: TextToSpeechEngine
    private lateinit var appDatabase: AppDatabase
    private lateinit var lessonDao: LessonDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var profileDao: ProfileDao
    private lateinit var dataSource: LocalLessonDataSource

    // repository
    lateinit var profileRepository: ProfileRepository
    val lessonExerciseRepository: LessonExerciseRepository
            by lazy { LessonExerciseRepository(dataSource) }
    val lessonRepository: LessonRepository by lazy { LessonRepository(dataSource) }

    override fun onCreate() {
        super.onCreate()
        initializeDataSource()
        profileRepository = PersistProfileRepository(profileDao)
    }

    private fun initializeDataSource() {
        textToSpeech = TextToSpeakUtil.create(this)
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