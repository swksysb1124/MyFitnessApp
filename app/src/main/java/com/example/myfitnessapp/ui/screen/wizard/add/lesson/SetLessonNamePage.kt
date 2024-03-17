package com.example.myfitnessapp.ui.screen.wizard.add.lesson

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.component.SettingWizardLayout
import com.example.myfitnessapp.ui.screen.wizard.WizardTextField
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun SetLessonNamePage(
    name: String,
    onBack: () -> Unit,
    onNameChange: (name: String) -> Unit,
    onNext: () -> Unit,
) {
    SettingWizardLayout(
        title = "設定名稱",
        onBack = onBack,
        onNext = onNext,
        nextEnabled = name.isNotEmpty(),
    ) {
        WizardTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            label = "名稱 (最長10個字)",
            isValueValid = { text -> text.length <= 10 },
            value = name,
            onValueChange = onNameChange
        )
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
fun AddLessonScreenPreview() {
    MyFitnessAppTheme {
        SetLessonNamePage("訓練內容",
            onNameChange = {},
            onBack = {},
            onNext = {}
        )
    }
}