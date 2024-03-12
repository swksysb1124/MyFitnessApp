package com.example.myfitnessapp.ui.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.DayOfWeek
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor

@Composable
fun DaysOfWeekView(
    daysOfWeek: Set<DayOfWeek>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        val daysOfWeekArray = arrayOf(false, false, false, false, false, false, false)
        daysOfWeek.forEach { day ->
            daysOfWeekArray[day.ordinal] = true
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeekArray.forEachIndexed { index, isDaySelected ->
                Text(
                    DayOfWeek.entries[index].value,
                    fontSize = 10.sp,
                    color = if (isDaySelected) backgroundColor else textColor,
                    modifier = Modifier
                        .padding(10.dp)
                        .drawBehind {
                            drawCircle(
                                style = if (isDaySelected) Fill else Stroke(width = 1.dp.toPx()),
                                color = textColor,
                                radius = this.size.maxDimension
                            )
                        }
                )
            }
        }
    }
}