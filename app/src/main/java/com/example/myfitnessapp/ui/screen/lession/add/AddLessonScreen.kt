package com.example.myfitnessapp.ui.screen.lession.add

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.WizardNaviPage

@Composable
fun AddLessonScreen(
    viewModel: AddLessonViewModel,
    onDismiss: () -> Unit
) {
    val name by viewModel.name.observeAsState(AddLessonViewModel.DEFAULT_LESSON_NAME)
    val startTime by viewModel.startTime.observeAsState(AddLessonViewModel.DEFAULT_LESSON_START_TIME)

    val wizardNaviController = rememberNavController()
    NavHost(
        navController = wizardNaviController,
        startDestination = WizardNaviPage.SelectExercises.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(WizardNaviPage.SelectExercises.route) {
            SelectExercisesPage(
                isExerciseSelected = viewModel::isExerciseSelected,
                onExerciseSelected = viewModel::updateExercises,
                onBack = onDismiss,
            ) {
                wizardNaviController.navigate(WizardNaviPage.SetStartTime.route)
            }
        }
        composable(WizardNaviPage.SetStartTime.route) {
            SetStartTimePage(
                startTime,
                onStartTimeChange = viewModel::updateStartTime,
                onBack = wizardNaviController::popBackStack
            ) {
                wizardNaviController.navigate(WizardNaviPage.SetName.route)
            }
        }
        composable(WizardNaviPage.SetName.route) {
            SetLessonNamePage(
                name,
                onNameChange = viewModel::updateName,
                onBack = wizardNaviController::popBackStack
            ) {
                // TODO show lesson information and confirm to save
            }
        }
    }
}
