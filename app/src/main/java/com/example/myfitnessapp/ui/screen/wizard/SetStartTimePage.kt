package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        nextEnabled = startTime.isNotEmpty()
    ) {
        WizardTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            label = "訓練時間 (小時:分鐘)",
            value = startTime,
            onValueChange = onStartTimeChange
        )
    }
}