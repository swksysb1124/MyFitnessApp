package com.example.myfitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
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
                MainScreen(viewModel = lessonViewModel)
            }
        }
    }
}