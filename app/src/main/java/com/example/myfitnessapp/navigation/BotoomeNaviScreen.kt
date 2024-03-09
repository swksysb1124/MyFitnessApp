package com.example.myfitnessapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BotoomeNaviScreen(val route: String, val name: String, val icon: ImageVector) {
    data object MyLesson : BotoomeNaviScreen(
        route = "my_lesson",
        name = "我的訓練",
        icon = Icons.Filled.DateRange
    )

    data object Profile : BotoomeNaviScreen(
        route = "profile",
        name = "我的檔案",
        icon = Icons.Filled.AccountCircle
    )
}