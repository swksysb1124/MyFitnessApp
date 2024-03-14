package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.myfitnessapp.database.AppDatabase
import com.example.myfitnessapp.database.ExerciseDao
import com.example.myfitnessapp.database.LessonDao
import com.example.myfitnessapp.datasource.DatabaseLessonDataSource
import com.example.myfitnessapp.datasource.LocalLessonDataSource
import com.example.myfitnessapp.navigation.NaviScreen
import com.example.myfitnessapp.navigation.WizardNaviPage
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
import com.example.myfitnessapp.ui.screen.lession.LessonContentScreen
import com.example.myfitnessapp.ui.screen.lession.LessonContentViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExercisePage
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModel
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.screen.wizard.AddLessonScreen
import com.example.myfitnessapp.ui.screen.wizard.AddLessonViewModel
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.animation.leftSlideInNaviAnimation
import com.example.myfitnessapp.util.animation.leftSlideOutNaviAnimation
import com.example.myfitnessapp.util.animation.rightSlideInNaviAnimation
import com.example.myfitnessapp.util.animation.rightSlideOutNaviAnimation
import com.example.myfitnessapp.util.tts.TextToSpeakUtil

class MainActivity : ComponentActivity() {
    // data source
    private lateinit var textToSpeech: TextToSpeakUtil
    private lateinit var appDatabase: AppDatabase
    private lateinit var lessonDao: LessonDao
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var dataSource: LocalLessonDataSource

    // repository
    private val lessonExerciseRepository: LessonExerciseRepository by lazy { LessonExerciseRepository(dataSource) }
    private val lessonRepository: LessonRepository by lazy { LessonRepository(dataSource) }
    private val profileRepository: ProfileRepository by lazy { ProfileRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataSource()

        setContent {
            MyFitnessAppTheme {
                val mainNavController = rememberNavController()
                NavHost(
                    navController = mainNavController,
                    startDestination = NaviScreen.Main.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable(
                        route = NaviScreen.Main.route,
                        enterTransition = {
                            when (initialState.destination.route) {
                                NaviScreen.Lesson.route -> rightSlideInNaviAnimation()
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                NaviScreen.Lesson.route -> leftSlideOutNaviAnimation()
                                else -> null
                            }
                        }
                    ) {
                        MainScreen(
                            lessonRepository = lessonRepository,
                            profileRepository = profileRepository,
                            onLessonClick = { id ->
                                mainNavController.navigate(
                                    NaviScreen.Lesson.createNaviRoute(id)
                                )
                            },
                            onAddLesson = {
                                mainNavController.navigate(WizardNaviPage.AddLessonMain.route)
                            }
                        )
                    }
                    composable(
                        route = NaviScreen.Lesson.route,
                        arguments = listOf(
                            navArgument(NaviScreen.LESSON_ID) { type = NavType.StringType }
                        ),
                        enterTransition = {
                            when (initialState.destination.route) {
                                NaviScreen.Main.route -> leftSlideInNaviAnimation()
                                NaviScreen.LessonExercise.route -> rightSlideInNaviAnimation()
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                NaviScreen.Main.route -> rightSlideOutNaviAnimation()
                                NaviScreen.LessonExercise.route -> leftSlideOutNaviAnimation()
                                else -> null
                            }
                        }
                    ) {
                        val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                        LessonContentScreen(
                            viewModel = viewModel(
                                key = lessonId
                            ) {
                                LessonContentViewModel(
                                    id = lessonId,
                                    lessonRepository = lessonRepository,
                                    lessonExerciseRepository = lessonExerciseRepository,
                                    profileRepository = profileRepository
                                )
                            },
                            onStartButtonClick = {
                                mainNavController.navigate(
                                    NaviScreen.LessonExercise.createNaviRoute(
                                        lessonId ?: ""
                                    )
                                )
                            },
                            onBackPressed = mainNavController::popBackStack
                        )
                    }
                    composable(
                        route = NaviScreen.LessonExercise.route,
                        arguments = listOf(
                            navArgument(NaviScreen.LESSON_ID) { type = NavType.StringType }
                        ),
                        enterTransition = {
                            when (initialState.destination.route) {
                                NaviScreen.Lesson.route -> leftSlideInNaviAnimation()
                                else -> null
                            }
                        },
                        exitTransition = {
                            when (targetState.destination.route) {
                                NaviScreen.Lesson.route -> rightSlideOutNaviAnimation()
                                else -> null
                            }
                        }
                    ) {
                        val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                        LessonExercisePage(
                            viewModel = viewModel(
                                key = lessonId
                            ) {
                                LessonExerciseViewModel(
                                    lessonId = lessonId,
                                    textToSpeech = textToSpeech,
                                    lessonExerciseRepository = lessonExerciseRepository
                                )
                            },
                            onBackPressed = mainNavController::popBackStack
                        )
                    }

                    composable(WizardNaviPage.AddLessonMain.route) {
                        AddLessonScreen(
                            viewModel = viewModel {
                                AddLessonViewModel(
                                    lessonRepository = lessonRepository,
                                    lessonExerciseRepository = lessonExerciseRepository
                                )
                            },
                            onDismiss = mainNavController::popBackStack,
                            onWizardSettingComplete = {
                                mainNavController.popBackStack()
                                // TODO notify main screen to refresh data
                            }
                        )
                    }
                }
            }
        }
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
        dataSource = DatabaseLessonDataSource(lessonDao, exerciseDao)
    }
}
