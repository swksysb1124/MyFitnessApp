package com.example.myfitnessapp.navigation

sealed class NaviScreen(
    val route: String,
) {
    data object Main : NaviScreen(
        route = "/main"
    )

    data object Lesson : NaviScreen(
        route = "/lesson/{$LESSON_ID}",
    ) {
        fun createNaviRoute(lessonId: String) = "/lesson/$lessonId"
    }

    data object LessonExercise : NaviScreen(
        route = "/lesson/exercise"
    )

    companion object {
        const val LESSON_ID = "lesson-id"
    }
}