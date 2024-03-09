package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.ui.screen.lession.LessonContentRoute
import com.example.myfitnessapp.ui.screen.lession.LessonContentScreen
import com.example.myfitnessapp.ui.screen.lession.LessonContentViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExercisePage
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseRoute
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModel
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.main.MainRoute
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.tts.TextToSpeakUtil

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var lessonViewModel: LessonContentViewModel
    private lateinit var textToSpeech: TextToSpeakUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        lessonViewModel = ViewModelProvider(this)[LessonContentViewModel::class.java]
        textToSpeech = TextToSpeakUtil.create(application)

        setContent {
            MyFitnessAppTheme {
                val mainNavController = rememberNavController()
                NavHost(
                    navController = mainNavController,
                    startDestination = MainRoute,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable(MainRoute) {
                        MainScreen(
                            loginViewModel = loginViewModel,
                            onLessonClick = {
                                mainNavController.navigate(LessonContentRoute)
                            }
                        )
                    }
                    composable(LessonContentRoute) {
                        LessonContentScreen(
                            viewModel = lessonViewModel,
                            onStartButtonClick = {
                                mainNavController.navigate(LessonExerciseRoute)
                            },
                            onBackPressed = {
                                mainNavController.popBackStack()
                            }
                        )
                    }
                    composable(LessonExerciseRoute) {
                        LessonExercisePage(
                            viewModel = viewModel(
                                initializer = {
                                    LessonExerciseViewModel(textToSpeech)
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