package com.example.myfitnessapp.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
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

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
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
    val shouldBottomNaviShown by mainViewModel.shouldBottomNaviShown.collectAsState(true)
    val density = LocalDensity.current
    Scaffold(
        containerColor = backgroundColor,
        bottomBar = {
            AnimatedVisibility(
                visible = shouldBottomNaviShown,
                enter = slideInVertically { with(density) { -40.dp.roundToPx() } }
                        + expandVertically(expandFrom = Alignment.Top)
                        + fadeIn(initialAlpha = 0.3f),
                exit = shrinkVertically() + fadeOut()
            ) {
                MainScreenBottomBar(
                    bottomNavController = bottomNavController,
                    bottomNaviScreen = bottomNaviScreen
                )
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
                    viewModel = viewModel {
                        MyLessonViewModel(
                            mainViewModel = mainViewModel,
                            lessonRepository = lessonRepository
                        )
                    },
                    onLessonClick = onLessonClick,
                    onAddLesson = onAddLesson,
                )
            }
            composable(BottomNaviScreen.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel {
                        ProfileViewModel(profileRepository)
                    }
                )
            }
        }
    }
}

@Composable
private fun MainScreenBottomBar(
    bottomNavController: NavHostController,
    bottomNaviScreen: List<BottomNaviScreen>
) {
    BottomNavigation(
        backgroundColor = Color(0xFF222831)
    ) {
        val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNaviScreen.forEach { screen ->
            MainScreenBottomNavigationItem(screen, currentDestination, bottomNavController)
        }
    }
}

@Composable
private fun RowScope.MainScreenBottomNavigationItem(
    screen: BottomNaviScreen,
    currentDestination: NavDestination?,
    bottomNavController: NavHostController
) {
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