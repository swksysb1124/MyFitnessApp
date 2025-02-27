package studio.jasonsu.myfitness.navigation

sealed class WizardNaviPage(val route: String) {
    data object AddLessonMain: WizardNaviPage("/add-lesson")
    data object SetName: WizardNaviPage("/name")
    data object SetStartTime: WizardNaviPage("/start-time")
    data object SelectExercises: WizardNaviPage("/select-exercises")
    data object ConfirmLessonContent: WizardNaviPage("/confirm-lesson-content")
}
