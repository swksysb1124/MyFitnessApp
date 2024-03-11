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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.BottomNaviScreen
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.screen.lession.MyLessonScreen
import com.example.myfitnessapp.ui.screen.lession.MyLessonViewModel
import com.example.myfitnessapp.ui.screen.profile.ProfileScreen
import com.example.myfitnessapp.ui.screen.profile.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    lessonRepository: LessonRepository,
    profileRepository: ProfileRepository,
    onLessonClick: (id: String) -> Unit = {},
    onAddLesson: () -> Unit = {}
) {
    val bottomNavController = rememberNavController()
    val bottomNaviScreen = listOf(
        BottomNaviScreen.MyLesson,
        BottomNaviScreen.Profile
    )

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = backgroundColor
            ) {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNaviScreen.forEach { screen ->
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
                            bottomNavController.navigate(screen.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) {
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
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNaviScreen.MyLesson.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(BottomNaviScreen.MyLesson.route) {
                MyLessonScreen(
                    viewModel = viewModel(
                        initializer = {
                            MyLessonViewModel(lessonRepository)
                        }
                    ),
                    onLessonClick = onLessonClick,
                    onAddLesson = onAddLesson
                )
            }
            composable(BottomNaviScreen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel(
                        initializer = {
                            ProfileViewModel(profileRepository)
                        }
                    )
                )
            }
        }
    }
}