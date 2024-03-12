package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.ui.color.textColor

@Composable
fun ExerciseSelectionRow(
    exercise: Exercise,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Image(
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp),
            painter = painterResource(id = exercise.icon),
            contentDescription = null
        )
        Column {
            Text(
                exercise.name,
                color = textColor,
                fontSize = 20.sp
            )
            Text("30分鐘", color = textColor)
        }
    }
}