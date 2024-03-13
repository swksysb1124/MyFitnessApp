package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun DaysOfWeekView(
    modifier: Modifier = Modifier,
    isDaySelected: (DayOfWeek) -> Boolean = { false },
    onDaySelected: (selected: Boolean, day: DayOfWeek) -> Unit = { _, _ -> }
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        val daysOfWeek = DayOfWeek.All
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                val density = LocalDensity.current
                val isSelected = isDaySelected(day)
                val (color, circleStyle) = when (isSelected) {
                    true -> backgroundColor to Fill
                    false -> textColor to Stroke(width = with(density) { 1.dp.toPx() })
                }
                Text(
                    text = day.value,
                    fontSize = 16.sp,
                    color = color,
                    modifier = Modifier
                        .padding(8.dp)
                        .drawBehind {
                            drawCircle(
                                style = circleStyle,
                                color = textColor,
                                radius = this.size.maxDimension * 0.8f
                            )
                        }
                        .clickable {
                            onDaySelected(!isSelected, day)
                        }
                )
            }
        }
    }
}

@Preview(widthDp = 400)
@Composable
fun DaysOfWeekViewPreview() {
    MyFitnessAppTheme {
        DaysOfWeekView(Modifier.padding(8.dp))
    }
}