package studio.jasonsu.myfitness.ui.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import studio.jasonsu.myfitness.R

@Composable
fun NavigationIndicatorIcon(
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
        contentDescription = stringResource(R.string.cd_navigate_forward),
        tint = tint
    )
}