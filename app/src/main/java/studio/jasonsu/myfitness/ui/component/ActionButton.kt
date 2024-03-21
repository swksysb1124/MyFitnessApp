package studio.jasonsu.myfitness.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    actionName: String,
    onAction: () -> Unit
) {
    Button(
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier,
        onClick = { onAction() }
    ) {
        Text(
            text = actionName,
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Preview(
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true,
    apiLevel = 33,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun ActionButtonPreview() {
    MyFitnessAppTheme {
        ActionButton(actionName = "開始", enabled = false) {

        }
    }
}