package com.example.myfitnessapp.ui.screen.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.Screen
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.lession.LessonScreen
import com.example.myfitnessapp.ui.screen.lession.LessonViewModel
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.plan.MyPlanScreen
import com.example.myfitnessapp.util.tts.TextToSpeakUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.MyLesson, Screen.Profile)

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = backgroundColor
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                screen.icon,
                                tint = Color.White,
                                contentDescription = null
                            )
                        },
                        label = { Text(screen.name, color = Color.White) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        val textToSpeech = TextToSpeakUtil.create(LocalContext.current.applicationContext)
        val lessonViewModel = LessonViewModel(textToSpeech)
        val loginViewModel = LoginViewModel()

        NavHost(
            navController = navController,
            startDestination = Screen.MyLesson.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None},
            exitTransition = { ExitTransition.None}
        ) {
            composable(Screen.MyLesson.route) {
                LessonScreen(lessonViewModel)
            }
            composable(Screen.Profile.route) {
                MyPlanScreen()
            }
        }
    }
}