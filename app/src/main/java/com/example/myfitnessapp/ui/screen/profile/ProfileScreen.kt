package com.example.myfitnessapp.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.backgroundColor
import com.example.myfitnessapp.ui.color.textColor
import com.example.myfitnessapp.ui.component.ScreenTitleRow

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val height by viewModel.heightInCm.observeAsState()
    val weight by viewModel.weightInKg.observeAsState()

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
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        )
        Divider(Modifier.padding(horizontal = 20.dp))
        ProfileInfoRow(
            label = "體重",
            value = "${weight}kg",
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
        )
        Divider(Modifier.padding(horizontal = 20.dp))
    }
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
        Text(value, color = textColor, fontSize = 20.sp)
    }
}