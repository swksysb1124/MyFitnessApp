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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.BotoomeNaviScreen
import com.example.myfitnessapp.ui.screen.backgroundColor
import com.example.myfitnessapp.ui.screen.login.LoginScreen
import com.example.myfitnessapp.ui.screen.login.LoginViewModel
import com.example.myfitnessapp.ui.screen.plan.MyPlanScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLessonClick: () -> Unit = {}
) {
    val bottomNavController = rememberNavController()
    val bottomNaviScreen = listOf(
        BotoomeNaviScreen.MyLesson,
        BotoomeNaviScreen.Profile
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
        val loginViewModel = LoginViewModel()

        NavHost(
            navController = bottomNavController,
            startDestination = BotoomeNaviScreen.MyLesson.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(BotoomeNaviScreen.MyLesson.route) { MyPlanScreen(onLessonClick = onLessonClick) }
            composable(BotoomeNaviScreen.Profile.route) { LoginScreen(viewModel = loginViewModel) }
        }
    }
}