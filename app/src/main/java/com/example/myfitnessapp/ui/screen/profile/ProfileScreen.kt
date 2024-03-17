package com.example.myfitnessapp.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.containerBackgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.component.ScreenTitleRow
import com.example.myfitnessapp.ui.icon.NavigationIndicatorIcon

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val height by viewModel.heightInCm.observeAsState(0.0)
    val weight by viewModel.weightInKg.observeAsState(0.0)
    val editDialogConfig by viewModel.editDialogConfig.observeAsState(
        ProfileEditDialogConfig(
            ProfileEditType.Height,
            isDialogOpen = false
        )
    )

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
    ) {
        ScreenTitleRow(title = "關於我")
        ProfileInfoRow(
            label = "身高",
            value = "${height}cm",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.openDialog(
                        value = height.toString(),
                        type = ProfileEditType.Height
                    )
                }
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        )
        RowDivider()
        ProfileInfoRow(
            label = "體重",
            value = "${weight}kg",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.openDialog(
                        value = weight.toString(),
                        type = ProfileEditType.Weight
                    )
                }
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        )
        RowDivider()
    }

    if (editDialogConfig.isDialogOpen) {
        ProfileInfoEditDialog(
            type = editDialogConfig.type,
            value = editDialogConfig.value,
            onValueChanged = { type, value ->
                viewModel.onSaved(type, value)
                viewModel.closeDialog(type)
            }
        ) {
            viewModel.closeDialog(editDialogConfig.type)
        }
    }
}

@Composable
private fun RowDivider() {
    HorizontalDivider(
        color = containerBackgroundColor,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

enum class ProfileEditType(val title: String) {
    Height("身高 (單位: cm)"),
    Weight("體重 (單位: kg)")
}

@Composable
private fun ProfileInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = textColor, fontSize = 20.sp)
        Row {
            Text(value, color = Color.White, fontSize = 20.sp)
            NavigationIndicatorIcon()
        }
    }
}
