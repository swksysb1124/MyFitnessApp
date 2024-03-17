package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfitnessapp.navigation.NaviScreen
import com.example.myfitnessapp.repository.LessonExerciseRepository
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
import com.example.myfitnessapp.ui.screen.lession.LessonContentScreen
import com.example.myfitnessapp.ui.screen.lession.LessonContentViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExercisePage
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModel
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.animation.leftSlideInNaviAnimation
import com.example.myfitnessapp.util.animation.leftSlideOutNaviAnimation
import com.example.myfitnessapp.util.animation.rightSlideInNaviAnimation
import com.example.myfitnessapp.util.animation.rightSlideOutNaviAnimation
import com.example.myfitnessapp.util.tts.TextToSpeakUtil

class MainActivity : ComponentActivity() {
    private lateinit var textToSpeech: TextToSpeakUtil
    private val lessonExerciseRepository: LessonExerciseRepository by lazy { LessonExerciseRepository() }
    private val lessonRepository: LessonRepository by lazy { LessonRepository() }
    private val profileRepository: ProfileRepository by lazy { ProfileRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeech = TextToSpeakUtil.create(application)

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
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        }
                    ) {
                        MainScreen(
                            lessonRepository = lessonRepository,
                            profileRepository = profileRepository,
                            onLessonClick = { id ->
                                mainNavController.navigate(
                                    NaviScreen.Lesson.createNaviRoute(id)
                                )
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
                                key = lessonId,
                                initializer = {
                                    LessonContentViewModel(
                                        id = lessonId,
                                        lessonRepository = lessonRepository,
                                        lessonExerciseRepository = lessonExerciseRepository,
                                        profileRepository = profileRepository
                                    )
                                }
                            ),
                            onStartButtonClick = {
                                mainNavController.navigate(
                                    NaviScreen.LessonExercise.createNaviRoute(
                                        lessonId ?: ""
                                    )
                                )
                            },
                            onBackPressed = {
                                mainNavController.popBackStack()
                            }
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
                                key = lessonId,
                                initializer = {
                                    LessonExerciseViewModel(
                                        id = lessonId,
                                        textToSpeech = textToSpeech,
                                        lessonExerciseRepository = lessonExerciseRepository
                                    )
                                }
                            ),
                            onBackPressed = {
                                mainNavController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
