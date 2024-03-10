package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myfitnessapp.navigation.NaviScreen
import com.example.myfitnessapp.ui.screen.lession.LessonContentScreen
import com.example.myfitnessapp.ui.screen.lession.LessonContentViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExercisePage
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModel
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.tts.TextToSpeakUtil

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var textToSpeech: TextToSpeakUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
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
                        route = NaviScreen.Main.route
                    ) {
                        MainScreen(
                            loginViewModel = loginViewModel,
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
                        )
                    ) {
                        val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                        LessonContentScreen(
                            viewModel = viewModel(
                                key = lessonId,
                                initializer = {
                                    LessonContentViewModel(id = lessonId)
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
                        )
                    ) {
                        val lessonId = it.arguments?.getString(NaviScreen.LESSON_ID)
                        LessonExercisePage(
                            viewModel = viewModel(
                                key = lessonId,
                                initializer = {
                                    LessonExerciseViewModel(
                                        id = lessonId,
                                        textToSpeech = textToSpeech
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
