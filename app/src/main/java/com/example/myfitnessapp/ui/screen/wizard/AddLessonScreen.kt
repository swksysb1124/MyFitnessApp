package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Time
import com.example.myfitnessapp.navigation.WizardNaviPage

@Composable
fun AddLessonScreen(
    viewModel: AddLessonViewModel,
    onDismiss: () -> Unit
) {
    val name by viewModel.name.observeAsState(AddLessonViewModel.DEFAULT_LESSON_NAME)
    val startTime by viewModel.startTime.observeAsState(Time(0, 0))
    val hasExerciseSelected by viewModel.hasExerciseSelected.observeAsState(false)
    val weekDescription by viewModel.weekDescription.observeAsState(DayOfWeek.Unspecified)

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
                nextEnabled = hasExerciseSelected
            ) {
                wizardNaviController.navigate(WizardNaviPage.SetStartTime.route)
            }
        }
        composable(WizardNaviPage.SetStartTime.route) {
            SetStartTimePage(
                startTime,
                onStartTimeChange = viewModel::updateStartTime,
                onBack = wizardNaviController::popBackStack,
                weekDescription = weekDescription,
                isDaySelected = viewModel::isDaySelected,
                onDaySelected = viewModel::onDaySelected
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
                wizardNaviController.navigate(WizardNaviPage.ConfirmLessonContent.route)
            }
        }
        composable(WizardNaviPage.ConfirmLessonContent.route) {
            LessonContentConfirmPage(
                viewModel = viewModel,
                onBack = wizardNaviController::popBackStack,
                onConfirm = onDismiss
            )
        }
    }
}
