package com.example.myfitnessapp.navigation

sealed class WizardNaviPage(val route: String) {
    data object SetName: WizardNaviPage("/name")
    data object SetStartTime: WizardNaviPage("/start-time")
}