package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.ui.color.backgroundColor

@Composable
fun SettingWizardLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    nextEnabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier
            .background(backgroundColor)
            .fillMaxSize()
            .imePadding()
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
                enabled = nextEnabled,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 36.dp,
                        top = 8.dp
                    ),
                actionName = "下一步"
            ) {
                onNext()
            }
        }
    }
}