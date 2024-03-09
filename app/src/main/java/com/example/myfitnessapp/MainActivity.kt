package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.ui.screen.lession.LessonContentRoute
import com.example.myfitnessapp.ui.screen.lession.LessonContentViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExercisePage
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseRoute
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonExerciseViewModelFactory
import com.example.myfitnessapp.ui.screen.lession.LessonContentScreen
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var lessonViewModel: LessonContentViewModel
    private lateinit var lessonExerciseViewModel: LessonExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        lessonViewModel = ViewModelProvider(this)[LessonContentViewModel::class.java]
        lessonExerciseViewModel = ViewModelProvider(
            this,
            LessonExerciseViewModelFactory(application)
        )[LessonExerciseViewModel::class.java]

        setContent {
            MyFitnessAppTheme {
                val mainNavController = rememberNavController()
                NavHost(
                    navController = mainNavController,
                    startDestination = "main",
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable("main") {
                        MainScreen(
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
                            viewModel = lessonExerciseViewModel,
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