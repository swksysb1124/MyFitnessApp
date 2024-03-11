package com.example.myfitnessapp.ui.screen.lession.add

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myfitnessapp.ui.component.SettingWizardLayout

@Composable
fun SetStartTimePage(
    startTime: String,
    onStartTimeChange: (String) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    SettingWizardLayout(
        title = "設定訓練時間",
        onBack = onBack,
        onNext = onNext,
    ) {
        TextField(
            modifier = Modifier.align(Alignment.Center),
            value = startTime,
            onValueChange = onStartTimeChange
        )
    }
}