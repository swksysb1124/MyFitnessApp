package studio.jasonsu.myfitness.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.model.DayOfWeek
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

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
        val backgroundColor = MaterialTheme.colorScheme.primary
        val selectedTextColor = MaterialTheme.colorScheme.onPrimary
        val unselectedTextColor = backgroundColor
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                val density = LocalDensity.current
                val isSelected = isDaySelected(day)
                val (color, circleStyle) = when (isSelected) {
                    true -> selectedTextColor to Fill
                    false -> unselectedTextColor to Stroke(width = with(density) { 1.dp.toPx() })
                }
                Text(
                    text = day.value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = color,
                    modifier = Modifier
                        .padding(8.dp)
                        .drawBehind {
                            drawCircle(
                                style = circleStyle,
                                color = backgroundColor,
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

@Preview(
    widthDp = 400,
    apiLevel = 33
)
@Composable
fun DaysOfWeekViewPreview() {
    MyFitnessAppTheme {
        DaysOfWeekView(Modifier.padding(8.dp))
    }
}