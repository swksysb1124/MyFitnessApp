package com.example.myfitnessapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingWizardLayout(
    modifier: Modifier = Modifier,
    type: WizardType = WizardType.Setting,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null,
    nextEnabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
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
        if (type == WizardType.Setting &&
            onNext != null
        ) {
            ActionNextButton(
                modifier = Modifier.align(Alignment.End),
                onNext = onNext,
                enabled = nextEnabled
            )
        }

        if (type == WizardType.Confirm &&
            onConfirm != null
        ) {
            ActionConfirmButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                onConfirm = onConfirm,
                enabled = nextEnabled
            )
        }
    }
}

enum class WizardType {
    Setting, Confirm
}

@Composable
private fun ActionNextButton(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    enabled: Boolean = true
) {
    ActionButton(
        enabled = enabled,
        modifier = modifier
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

@Composable
private fun ActionConfirmButton(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    enabled: Boolean = true
) {
    ActionButton(
        enabled = enabled,
        modifier = modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 36.dp,
                top = 8.dp
            ),
        actionName = "確認"
    ) {
        onConfirm()
    }
}