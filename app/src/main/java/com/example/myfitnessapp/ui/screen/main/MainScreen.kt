package com.example.myfitnessapp.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myfitnessapp.ui.screen.lession.LessonScreen
import com.example.myfitnessapp.ui.screen.lession.LessonViewModel
import com.example.myfitnessapp.ui.screen.lession.backgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: LessonViewModel
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = backgroundColor
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.DateRange,
                            tint = Color.White,
                            contentDescription = null
                        )
                    },
                    label = { Text("我的訓練", color = Color.White) },
                    selected = true,
                    onClick = {
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.AccountCircle,
                            tint = Color.White,
                            contentDescription = null
                        )
                    },
                    label = { Text("我的檔案", color = Color.White) },
                    selected = false,
                    onClick = {
                    }
                )

            }
        }
    ) { innerPadding ->
        LessonScreen(viewModel = viewModel, modifier = Modifier.padding(innerPadding))
    }
}