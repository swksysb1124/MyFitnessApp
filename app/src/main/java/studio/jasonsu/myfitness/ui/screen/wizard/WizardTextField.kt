package studio.jasonsu.myfitness.ui.screen.wizard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WizardTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    isValueValid: (String) -> Boolean = { true },
    readOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Row(modifier = modifier) {
        TextField(
            enabled = enabled,
            readOnly = readOnly,
            label = { Text(text = label) },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            textStyle = TextStyle(
                fontSize = 24.sp,
                lineHeight = 25.sp
            ),
            colors = TextFieldDefaults.colors(),
            value = value,
            onValueChange = {
                if (isValueValid(it)) {
                    onValueChange(it)
                }
            },
            keyboardOptions = keyboardOptions
        )
    }
}