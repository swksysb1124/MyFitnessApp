package com.example.myfitnessapp.ui.screen.lession.add

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.navigation.WizardNaviPage
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun AddLessonScreen(
    viewModel: AddLessonViewModel,
    onDismiss: () -> Unit
) {
    val name by viewModel.name.observeAsState("")
    val startTime by viewModel.startTime.observeAsState("00:00")

    val wizardNaviController = rememberNavController()
    NavHost(
        navController = wizardNaviController,
        startDestination = WizardNaviPage.SetName.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(WizardNaviPage.SetName.route) {
            SetLessonNamePage(
                name,
                onNameChange = {
                    viewModel.updateName(it)
                },
                onBack = onDismiss,
                onNext = {
                    wizardNaviController.navigate(WizardNaviPage.SetStartTime.route)
                }
            )
        }
        composable(WizardNaviPage.SetStartTime.route) {
            SetStartTimePage(
                startTime,
                onStartTimeChange = {
                    viewModel.updateStartTime(it)
                },
                onBack = {
                    wizardNaviController.popBackStack()
                },
                onNext = {

                }
            )
        }
    }
}
