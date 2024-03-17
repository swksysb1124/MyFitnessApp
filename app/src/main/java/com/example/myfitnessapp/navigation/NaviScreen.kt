package com.example.myfitnessapp.navigation

sealed class NaviScreen(
    val route: String,
) {
    data object AddProfile: NaviScreen(
        route = "/add-profile"
    )

    data object Main : NaviScreen(
        route = "/main"
    )

    data object Lesson : NaviScreen(
        route = "/lesson/{$LESSON_ID}",
    ) {
        fun createNaviRoute(lessonId: String) = "/lesson/$lessonId"
    }

    data object LessonExercise : NaviScreen(
        route = "/lesson/exercise/{$LESSON_ID}"
    ) {
        fun createNaviRoute(lessonId: String) = "/lesson/exercise/$lessonId"
    }

    companion object {
        const val LESSON_ID = "lesson-id"
    }
}