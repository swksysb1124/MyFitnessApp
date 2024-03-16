package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.model.Time
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.buttonBackgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.component.DaysOfWeekView
import com.example.myfitnessapp.ui.component.SettingWizardLayout
import com.example.myfitnessapp.ui.dialog.TimePickerDialog
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetStartTimePage(
    startTime: Time,
    weekDescription: String = DayOfWeek.Unspecified,
    onStartTimeChange: (time: Time) -> Unit = {},
    isDaySelected: (day: DayOfWeek) -> Boolean = { false },
    onDaySelected: (selected: Boolean, day: DayOfWeek) -> Unit = { _, _ -> },
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    val state = rememberTimePickerState(
        initialHour = startTime.hour,
        initialMinute = startTime.minute
    )
    var isTimePickerOpen by remember { mutableStateOf(false) }

    SettingWizardLayout(
        title = "設定訓練時間",
        onBack = onBack,
        onNext = onNext,
    ) {
        Column {
            WizardTextField(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    )
                    .clickable {
                        isTimePickerOpen = true
                    },
                enabled = false,
                label = "開始時間",
                value = startTime.toString()
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
                        tint = backgroundColor,
                        imageVector = Icons.Sharp.DateRange,
                        contentDescription = null
                    )
                    Text(
                        weekDescription,
                        color = backgroundColor,
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
    if (isTimePickerOpen) {
        TimePickerDialog(
            onDismissRequest = {
                isTimePickerOpen = false
            },
            confirmButton = {
                Button(
                    onClick = {
                        onStartTimeChange(
                            Time(state.hour, state.minute)
                        )
                        isTimePickerOpen = false
                    }
                ) {
                    Text("確認")
                }
            },
            content = {
                TimePicker(state = state)
            }
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun SetStartTimePagePreview() {
    MyFitnessAppTheme {
        SetStartTimePage(
            startTime = Time(21, 10)
        )
    }
}
