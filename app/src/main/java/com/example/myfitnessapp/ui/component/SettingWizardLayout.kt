package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.backgroundColor

@Composable
fun SettingWizardLayout(
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Column(
        Modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        ScreenTitleRow(title = title ?: "", onBackPressed = onBack)
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            content()
        }
        if (onNext != null) {
            ActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                actionName = "下一步"
            ) {
                onNext()
            }
        }
    }
}