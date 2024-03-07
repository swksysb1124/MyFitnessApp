package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.model.Activity
import com.example.myfitnessapp.model.Exercise
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme
import com.example.myfitnessapp.util.formattedDuration

@Composable
fun ExerciseRow(
    activity: Activity<Exercise>,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) {
    val exercise = activity.content
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = exercise.icon),
            contentDescription = null
        )
        Text(
            text = exercise.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Text(
            text = activity.durationInSecond.formattedDuration(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}

@Preview
@Composable
fun ExerciseRowPreview() {
    MyFitnessAppTheme {
        ExerciseRow(
            activity = Activity(Exercise.Squat, 30),
            onItemClick = {}
        )
    }
}