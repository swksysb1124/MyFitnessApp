package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundCornerStatusRow(
    statusList: List<Status>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
    ) {
        statusList.onEachIndexed { index, status ->
            val shape = getStatusShape(index, statusList)
            val textColor = Color(0xFF35374B)
            val backgroundColor = Color(0xFF78A083)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(70.dp)
                    .background(color = backgroundColor, shape = shape)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    status.label,
                    fontSize = 16.sp,
                    color = textColor
                )
                Text(
                    status.status,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
            if (index != statusList.lastIndex) {
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
    }
}

private fun getStatusShape(
    index: Int,
    statusList: List<Status>
) = when {
    (index == 0 && index == statusList.lastIndex) ->
        RoundedCornerShape(20.dp)

    (index == 0) ->
        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)

    (index == statusList.lastIndex) ->
        RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)

    else -> RectangleShape
}

data class Status(
    val label: String,
    val status: String
)