package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.ui.theme.RaspberryControllerAppTheme

@Composable
fun CardButton(onClick: () -> Unit, text: String, icon: ImageVector? = null) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)

    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .heightIn(min = 125.dp)
                .background(
                    brush = Brush
                        .linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.background,
                            ),
                        )
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(3f)
                        .align(Alignment.CenterVertically),
                )
                val iconSize = 125.dp * 0.5f
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Info Icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .size(iconSize)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .size(iconSize)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardButtonPreview() {
    RaspberryControllerAppTheme {
        CardButton(onClick = { /*TODO*/ }, text = "Card Button")
    }

}