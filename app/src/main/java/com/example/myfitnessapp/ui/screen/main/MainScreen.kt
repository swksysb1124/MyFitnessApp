package com.example.myfitnessapp.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.BottomNaviScreen
import com.example.myfitnessapp.repository.LessonRepository
import com.example.myfitnessapp.repository.ProfileRepository
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
        containerColor = MaterialTheme.colorScheme.primaryContainer,
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
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        bottomNaviScreen.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.name,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = { Text(screen.name, color = MaterialTheme.colorScheme.onPrimary) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
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
