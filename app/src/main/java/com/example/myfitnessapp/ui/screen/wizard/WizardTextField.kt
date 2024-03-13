package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    enabled: Boolean = true,
) {
    Row(modifier = modifier) {
        TextField(
            enabled = enabled,
            readOnly = readOnly,
            label = { Text(text = label) },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            textStyle = TextStyle(
                fontSize = 24.sp,
                lineHeight = 25.sp
            ),
            colors = TextFieldDefaults.textFieldColors(),
            value = value,
            onValueChange = onValueChange
        )
    }
}