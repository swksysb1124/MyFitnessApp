package studio.jasonsu.myfitness.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val name by viewModel.name.collectAsStateWithLifecycle()
    val phoneNumber by viewModel.phoneNumber.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val isNameExceedMaxLength by viewModel.isNameExceedMaxLength.collectAsStateWithLifecycle()

    Scaffold { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            ) {
                ListItem(
                    title = "姓名",
                    value = name,
                    onValueChange = viewModel::onNameChanged,
                    isError = isNameExceedMaxLength
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "${name.length}/${LoginViewModel.MAX_NAME_LENGTH}",
                    color = if (isNameExceedMaxLength) Color.Red else Color.Unspecified
                )
                Spacer(modifier = Modifier.height(5.dp))
                ListItem(
                    title = "電話",
                    value = phoneNumber,
                    onValueChange = viewModel::onPhoneNumberChanged
                )
                Spacer(modifier = Modifier.height(5.dp))
                ListItem(
                    title = "信箱",
                    value = email,
                    onValueChange = viewModel::onEmailChanged
                )

                Button(onClick = {

                }) {
                    Text(text = "發送")
                }
            }
        }
    }


}

@Composable
private fun ListItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    maxTextLength: Int = 100
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                if (it.length <= maxTextLength) {
                    onValueChange(it)
                } else {
                    onValueChange(it.substring(0, maxTextLength))
                }
            },
            placeholder = { Text(text = "輸入$title") }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MyFitnessAppTheme {
        LoginScreen(LoginViewModel())
    }
}