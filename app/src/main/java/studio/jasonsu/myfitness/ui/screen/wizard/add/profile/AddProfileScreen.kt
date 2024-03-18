package studio.jasonsu.myfitness.ui.screen.wizard.add.profile

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import studio.jasonsu.myfitness.repository.MockProfileRepository
import studio.jasonsu.myfitness.ui.component.SettingWizardLayout
import studio.jasonsu.myfitness.ui.component.WizardType
import studio.jasonsu.myfitness.ui.screen.wizard.WizardTextField
import studio.jasonsu.myfitness.ui.theme.MyFitnessAppTheme

@Composable
fun AddProfileScreen(
    viewModel: AddProfileViewModel,
    onConfirm: () -> Unit,
    onFinished: () -> Unit
) {
    val height by viewModel.height.collectAsState("")
    val weight by viewModel.weight.collectAsState("")
    val isFieldsAllValid by viewModel.isFieldsAllValid.collectAsState(true)

    BackHandler {
        onFinished()
    }

    SettingWizardLayout(
        title = "請輸入你的身高體重",
        type = WizardType.Confirm,
        nextOrConfirmEnabled = isFieldsAllValid,
        onConfirm = {
            viewModel.save(onConfirm)
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "我們會參考您的體重，進行消耗卡路里的概算。您可以稍後在 [關於我] 頁面進行修改。",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            WizardTextField(
                label = "身高(單位: cm)",
                value = height,
                isValueValid = ::isPositiveInteger,
                onValueChange = viewModel::onHeightChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            WizardTextField(
                label = "體重(單位: kg)",
                value = weight,
                isValueValid = ::isPositiveInteger,
                onValueChange = viewModel::onWightChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}


private fun isPositiveInteger(it: String): Boolean {
    return !it.contains(",") &&
            !it.contains("-") &&
            !it.contains(".") &&
            (it.toIntOrNull() ?: 0) >= 0
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
fun AddProfileScreenPreview() {
    val viewModel = AddProfileViewModel(MockProfileRepository())
    MyFitnessAppTheme {
        AddProfileScreen(
            viewModel = viewModel,
            onConfirm = {},
            onFinished = {}
        )
    }
}