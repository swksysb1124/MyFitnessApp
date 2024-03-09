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
import com.example.myfitnessapp.ui.screen.lession.LessonScreen
import com.example.myfitnessapp.ui.screen.lession.LessonViewModel
import com.example.myfitnessapp.ui.screen.lession.LessonViewModelFactory
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.main.MainScreen
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var lessonViewModel: LessonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        lessonViewModel = ViewModelProvider(
            this,
            LessonViewModelFactory(application)
        )[LessonViewModel::class.java]

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
                        LessonScreen(
                            viewModel = lessonViewModel,
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