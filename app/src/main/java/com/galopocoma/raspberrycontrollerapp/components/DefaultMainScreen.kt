package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.CPUUsageCallback
import com.galopocoma.raspberrycontrollerapp.models.CPUUsage
import kotlinx.coroutines.delay

@Composable
fun DefaultMainScreen() {
    Scaffold(
        topBar = {
            MainTopBar()
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Contenido vacío
            }
        },
        bottomBar = {
            MainBottomBar()
        }
    )
}

@Composable
fun MainBottomBar() {
    val controller = RaspberryPiController()
    val currentCPUUsage = remember { mutableStateOf(0.0) }
    val updateInterval = 4000L // Intervalo de actualización en milisegundos (4 segundos)

    LaunchedEffect(key1 = currentCPUUsage) {
        while (true) {
            controller.fetchCPUUsage(object : CPUUsageCallback {
                override fun onSuccess(cpuUsage: CPUUsage) {
                    currentCPUUsage.value = cpuUsage.cpuUsagePercent
                }

                override fun onError(message: String) {
                    currentCPUUsage.value = 0.0
                }
            })
            delay(updateInterval)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .heightIn(24.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CPU Usage: ${String.format("%.4f", currentCPUUsage.value)} %",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}