package com.example.myfitnessapp.ui.screen.wizard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.component.SettingWizardLayout
import com.example.myfitnessapp.ui.theme.MyFitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class)
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
        nextEnabled = name.isNotEmpty()
    ) {
        WizardTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                ),
            label = "名稱 (最長20個字)",
            value = name,
            onValueChange = onNameChange
        )
    }
}

@Preview
@Composable
fun AddLessonScreenPreview() {
    MyFitnessAppTheme {
        SetLessonNamePage("",
            onNameChange = {},
            onBack = {},
            onNext = {}
        )
    }
}