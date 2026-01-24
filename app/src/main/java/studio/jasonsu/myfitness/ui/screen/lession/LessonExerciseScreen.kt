package studio.jasonsu.myfitness.ui.screen.lession

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import studio.jasonsu.myfitness.model.Exercise
import studio.jasonsu.myfitness.model.Rest
import studio.jasonsu.myfitness.ui.component.ScreenTitleRow
import studio.jasonsu.myfitness.util.KeepScreenOn
import studio.jasonsu.myfitness.util.formattedDuration

@Composable
fun LessonExercisePage(
    viewModel: LessonExerciseViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    val currentExercise by viewModel.currentExercise.collectAsStateWithLifecycle()
    val timeLeftInSecond by viewModel.timeLeft.collectAsState(0)
    val onExerciseClose by viewModel.onExerciseClose.collectAsStateWithLifecycle()

    LaunchedEffect(onExerciseClose) {
        when (onExerciseClose) {
            ExerciseCloseReason.NotYetClose -> Unit
            ExerciseCloseReason.Finished -> onBackPressed()
            ExerciseCloseReason.Stopped -> {
                viewModel.stopLesson()
                onBackPressed()
            }
        }
    }

    val backgroundColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onSurface
    KeepScreenOn()
    Surface(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        Box(modifier.background(backgroundColor)) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(backgroundColor)
            ) {
                if (currentExercise is Exercise) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        painter = painterResource(id = (currentExercise as Exercise).metaData.icon),
                        contentDescription = null
                    )
                }
                val activityName = when (currentExercise) {
                    is Exercise -> (currentExercise as Exercise).name
                    is Rest -> (currentExercise as Rest).name
                    else -> ""
                }
                Text(
                    text = activityName,
                    fontSize = 40.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = timeLeftInSecond.formattedDuration(),
                    fontSize = 50.sp,
                    color = textColor,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            ScreenTitleRow(
                title = "",
                onBackPressed = {
                    viewModel.stopLesson()
                    onBackPressed()
                }
            )
        }
    }
}
