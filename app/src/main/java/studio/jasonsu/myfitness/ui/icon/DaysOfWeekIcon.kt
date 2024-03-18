package studio.jasonsu.myfitness.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DaysOfWeekIcon(
    tint: Color = LocalContentColor.current,
) {
    Icon(
        modifier = Modifier.size(25.dp),
        tint = tint,
        imageVector = Icons.Sharp.DateRange,
        contentDescription = null
    )
}