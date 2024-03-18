package studio.jasonsu.myfitness.ui.screen.wizard.add.lesson

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.model.ExerciseMetaData
import studio.jasonsu.myfitness.ui.component.SelectCheckBox
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme
import studio.jasonsu.myfitness.util.spokenDuration

@Composable
fun ExerciseSelectionRow(
    metaData: ExerciseMetaData,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val textColor = MaterialTheme.colorScheme.onSurface
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
                color = textColor,
                fontSize = 20.sp
            )
            Text(
                text = metaData.suggestedDurationInSecond.spokenDuration(),
                color = textColor
            )
        }
    }
}

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ExerciseSelectionRowPreview() {
    MyFitnessAppTheme {
        ExerciseSelectionRow(
            ExerciseMetaData.HighStrengthRunning,
            false,
            onCheckedChange = {}
        )
    }
}