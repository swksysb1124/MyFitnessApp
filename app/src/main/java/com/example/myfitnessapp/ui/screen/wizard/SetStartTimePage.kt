package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.ui.color.buttonBackgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.component.DaysOfWeekView
import com.example.myfitnessapp.ui.component.SettingWizardLayout
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun SetStartTimePage(
    startTime: String,
    weekDescription: String = DayOfWeek.Unspecified,
    onStartTimeChange: (time: String) -> Unit = {},
    isDaySelected: (day: DayOfWeek) -> Boolean = { false },
    onDaySelected: (selected: Boolean, day: DayOfWeek) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    SettingWizardLayout(
        title = "設定訓練時間",
        onBack = onBack,
        onNext = onNext,
        nextEnabled = startTime.isNotEmpty()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            WizardTextField(
                modifier = Modifier
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                label = "訓練時間 (小時:分鐘)",
                value = startTime,
                onValueChange = onStartTimeChange
            )
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        tint = buttonBackgroundColor,
                        imageVector = Icons.Sharp.DateRange,
                        contentDescription = null
                    )
                    Text(
                        weekDescription,
                        color = textColor,
                        fontSize = 18.sp
                    )
                }
                DaysOfWeekView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 12.dp
                        ),
                    isDaySelected = isDaySelected,
                    onDaySelected = onDaySelected
                )
            }
        }
    }
}

@Preview
@Composable
fun SetStartTimePagePreview() {
    MyFitnessAppTheme {
        SetStartTimePage(
            startTime = "20:00"
        )
    }
}
