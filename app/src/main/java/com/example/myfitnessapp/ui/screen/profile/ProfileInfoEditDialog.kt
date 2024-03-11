package com.example.myfitnessapp.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInfoEditDialog(
    type: ProfileEditType,
    value: String?,
    onValueChanged: (type: ProfileEditType, value: String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var text by remember { mutableStateOf(value ?: "") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                "編輯",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .align(Alignment.CenterHorizontally),
                textStyle = TextStyle(fontSize = 20.sp),
                value = text,
                onValueChange = {
                    if (it.isEmpty() || it.isDigitsOnly()) {
                        text = it
                    }
                },
                label = {
                    Text(type.title)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Button(
                enabled = text.isNotEmpty(),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .align(Alignment.End),
                onClick = {
                    onValueChanged(type, text)
                }
            ) {
                Text("儲存")
            }
        }
    }
}