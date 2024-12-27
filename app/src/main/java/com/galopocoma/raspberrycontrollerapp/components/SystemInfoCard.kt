package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.SystemInfoCallback
import com.galopocoma.raspberrycontrollerapp.models.SystemInfo

@Composable
fun SystemInfoCard(modifier: Modifier = Modifier) {
    val controller = RaspberryPiController()
    val info = remember { mutableStateOf<SystemInfo?>(null) }

    LaunchedEffect(Unit) {
        controller.fetchSystemInfo(object : SystemInfoCallback {
            override fun onSuccess(systemInfo: SystemInfo) {
                info.value = systemInfo
            }

            override fun onError(message: String) {
                info.value = null
            }
        })
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Información del Sistema",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (info.value != null) {
                SystemInfoRow(label = "Hostname", value = info.value!!.hostname)
                SystemInfoRow(label = "Uptime", value = formatUptime(info.value!!.uptime))
                SystemInfoRow(label = "Boot Time", value = info.value!!.bootTime)
                SystemInfoRow(label = "OS", value = info.value!!.os)
                SystemInfoRow(label = "Platform", value = info.value!!.platform)
                SystemInfoRow(label = "Platform Family", value = info.value!!.platformFamily)
                SystemInfoRow(label = "Platform Version", value = info.value!!.platformVersion)
                SystemInfoRow(label = "Kernel Version", value = info.value!!.kernelVersion)
                SystemInfoRow(label = "Virtualization System", value = info.value!!.virtualizationSystem)
                SystemInfoRow(label = "Virtualization Role", value = info.value!!.virtualizationRole)
            } else {
                Text(text = "No se pudo obtener la información del sistema")
            }
        }
    }
}

@Composable
fun SystemInfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.weight(1f)
        )
    }
}

fun formatUptime(uptime: String): String {
    val totalSeconds = uptime.toLongOrNull() ?: return uptime
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "$minutes minutes, $seconds seconds"
}