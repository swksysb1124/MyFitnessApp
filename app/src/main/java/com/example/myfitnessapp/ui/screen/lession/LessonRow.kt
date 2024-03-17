package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.ui.color.rowBackgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun LessonRow(
    modifier: Modifier = Modifier,
    name: String? = null,
    screenMode: LessonScreenMode = LessonScreenMode.Normal,
    startTime: String,
    duration: String,
    daysOfWeek: List<DayOfWeek> = emptyList(),
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(rowBackgroundColor, shape = RoundedCornerShape(20.dp))
            .height(120.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (screenMode == LessonScreenMode.Edit) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = onCheckedChange
                )
            }
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                if (name != null) {
                    Text(
                        text = name,
                        fontSize = 24.sp,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(duration, fontSize = 16.sp, color = textColor)
                val daysOfWeekDes = DayOfWeek.generateWeekDescription(daysOfWeek)
                Text(
                    daysOfWeekDes,
                    color = textColor,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(startTime, fontSize = 24.sp, color = textColor, textAlign = TextAlign.Center)
        }
    }
}

@Preview(widthDp = 350, apiLevel = 33)
@Composable
fun LessonRowPreview() {
    MyFitnessAppTheme {
        LessonRow(
            screenMode = LessonScreenMode.Edit,
            name = "晨間運動",
            startTime = "18:00",
            duration = "10小時59分59秒",
            daysOfWeek = listOf(
                DayOfWeek.SAT,
                DayOfWeek.SUN,
                DayOfWeek.MON,
                DayOfWeek.TUE,
                DayOfWeek.WED,
                DayOfWeek.FRI
            )
        )
    }
}