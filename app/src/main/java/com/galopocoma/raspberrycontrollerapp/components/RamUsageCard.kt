package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import kotlinx.coroutines.delay

@Composable
fun RamUsageCard(ramUsage: RAMUsage) {
    val ramUsageHistory = remember { mutableStateListOf<RAMUsage>() }
    val updateInterval = 4000L
    val maxHistorySize = 9

    LaunchedEffect(ramUsage) {
        while (true) {
            ramUsageHistory.add(ramUsage.copy())
            if (ramUsageHistory.size > maxHistorySize) {
                ramUsageHistory.removeAt(0)
            }
            delay(updateInterval)
        }
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "RAM Usage",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Legend()
            Spacer(modifier = Modifier.height(8.dp))
            RamUsageGraph(ramUsageHistory, maxHistorySize)
        }
    }
}

@Composable
fun Legend() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        LegendItem(color = Color(0xFF286140), label = "Total RAM")
        Spacer(modifier = Modifier.width(16.dp))
        LegendItem(color = Color(0xFF6f263d), label = "Used RAM")
        Spacer(modifier = Modifier.width(16.dp))
        LegendItem(color = Color(0xFF4A8C6F), label = "Free RAM")
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier = Modifier.size(20.dp)) {
            drawRect(color = color)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = Color.Black, fontSize = 12.sp)
    }
}

@Composable
fun RamUsageGraph(ramUsageHistory: List<RAMUsage>, maxHistorySize: Int) {
    val barWidth = 25.dp
    val maxRam = ramUsageHistory.maxOfOrNull { it.totalMB } ?: 1.0

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
            )
    ) {
        val initialOffset = 40.dp.toPx()
        val barSpacing =
            (size.width - initialOffset - (barWidth.toPx() * maxHistorySize)) / (maxHistorySize + 1)
        var xOffset = initialOffset + barSpacing

        // Draw grid lines and labels
        val gridLineCount = 4
        val gridLineSpacing = size.height / gridLineCount
        for (i in 0..gridLineCount) {
            val y = i * gridLineSpacing
            drawLine(
                color = Color.Gray.copy(alpha = 0.5f),
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(size.width, y),
                strokeWidth = 1.dp.toPx()
            )
            val label = (maxRam * (gridLineCount - i) / gridLineCount).toInt().toString() + " MB"
            drawContext.canvas.nativeCanvas.drawText(
                label,
                0f,
                y + 12.sp.toPx() / 2,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 12.sp.toPx()
                }
            )
        }

        // Draw stacked bars
        ramUsageHistory.takeLast(maxHistorySize).forEach { usage ->
            val totalHeight = ((usage.totalMB / maxRam) * size.height).toFloat()
            val usedHeight = ((usage.usedMB / maxRam) * size.height).toFloat()
            val freeHeight = ((usage.freeMB / maxRam) * size.height).toFloat()

            drawRect(
                color = Color(0xFF286140), // Total RAM color
                topLeft = androidx.compose.ui.geometry.Offset(
                    xOffset,
                    size.height - totalHeight
                ),
                size = androidx.compose.ui.geometry.Size(barWidth.toPx(), totalHeight)
            )
            drawRect(
                color = Color(0xFF6f263d), // Used RAM color
                topLeft = androidx.compose.ui.geometry.Offset(xOffset, size.height - usedHeight),
                size = androidx.compose.ui.geometry.Size(barWidth.toPx(), usedHeight)
            )
            drawRect(
                color = Color(0xFF4A8C6F), // Free RAM color
                topLeft = androidx.compose.ui.geometry.Offset(
                    xOffset,
                    size.height - usedHeight - freeHeight
                ),
                size = androidx.compose.ui.geometry.Size(barWidth.toPx(), freeHeight)
            )

            xOffset += barWidth.toPx() + barSpacing
        }
    }
}