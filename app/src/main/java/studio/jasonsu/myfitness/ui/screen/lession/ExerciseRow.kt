package studio.jasonsu.myfitness.ui.screen.lession

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.R
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme
import studio.jasonsu.myfitness.util.spokenDuration

@Composable
fun ExerciseRow(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) {
    val name = exercise.name
    val duration = exercise.durationInSecond.spokenDuration()
    val headIcon = exercise.metaData.icon
    val textColor = MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(70.dp).padding(5.dp),
            painter = painterResource(id = headIcon),
            contentDescription = stringResource(R.string.cd_exercise_icon)
        )
        Text(
            text = name,
            fontSize = 20.sp,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        )
        Text(
            text = duration,
            fontSize = 20.sp,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
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
            exercise = Exercise.create(ExerciseMetaData.Squat, 30),
            onItemClick = {}
        )
    }
}