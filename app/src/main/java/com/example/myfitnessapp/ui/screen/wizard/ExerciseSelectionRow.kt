package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.ExerciseMetaData
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.component.SelectCheckBox
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.spokenDuration

@Composable
fun ExerciseSelectionRow(
    metaData: ExerciseMetaData,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectCheckBox(
            modifier = Modifier.padding(horizontal = 12.dp),
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Image(
            modifier = Modifier
                .size(40.dp),
            painter = painterResource(id = metaData.icon),
            contentDescription = null
        )
        Column {
            Text(
                text = metaData.name,
                fontWeight = FontWeight.Bold,
                color = backgroundColor,
                fontSize = 20.sp
            )
            Text(
                text = metaData.suggestedDurationInSecond.spokenDuration(),
                fontWeight = FontWeight.Bold,
                color = backgroundColor
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ExerciseSelectionRowPreview() {
    MyFitnessAppTheme {
        ExerciseSelectionRow(
            ExerciseMetaData.HighStrengthRunning,
            true,
            onCheckedChange = {}
        )
    }
}