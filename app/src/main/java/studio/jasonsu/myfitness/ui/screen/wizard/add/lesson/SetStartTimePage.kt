package studio.jasonsu.myfitness.ui.screen.wizard.add.lesson

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.model.Time
import studio.jasonsu.myfitness.ui.component.DaysOfWeekView
import studio.jasonsu.myfitness.ui.component.SettingWizardLayout
import studio.jasonsu.myfitness.ui.dialog.TimePickerDialog
import studio.jasonsu.myfitness.ui.icon.DaysOfWeekIcon
import studio.jasonsu.myfitness.ui.screen.wizard.WizardTextField
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

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
                    DaysOfWeekIcon(
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        weekDescription,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
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

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun SetStartTimePagePreview() {
    MyFitnessAppTheme {
        SetStartTimePage(
            startTime = Time(21, 10)
        )
    }
}
