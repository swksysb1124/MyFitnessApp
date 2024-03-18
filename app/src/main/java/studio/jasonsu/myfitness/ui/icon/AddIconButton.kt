package studio.jasonsu.myfitness.ui.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddIconButton(
    tint: Color = LocalContentColor.current,
    onAddLesson: () -> Unit
) {
    IconButton(
        onClick = onAddLesson
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            tint = tint
        )
    }
}