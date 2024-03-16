package com.example.myfitnessapp.ui.screen.lession

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfitnessapp.ui.color.rowBackgroundColor
import com.example.myfitnessapp.ui.color.textColor

@Composable
fun RoundCornerStatusRow(
    statusList: List<Status>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        statusList.onEachIndexed { index, status ->
            val shape = getStatusShape(index, statusList)
            val backgroundColor = rowBackgroundColor
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = backgroundColor, shape = shape)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    status.label,
                    fontSize = 16.sp,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(5.dp))
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