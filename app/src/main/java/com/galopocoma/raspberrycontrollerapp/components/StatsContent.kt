package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.galopocoma.raspberrycontrollerapp.controllers.MinidlnaStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.TransmissionStatusCallback
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus

@Composable
fun StatsContent() {
    val controller = remember { RaspberryPiController() }
    val minidlna = remember { mutableStateOf(false) }
    val transmission = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        controller.fetchMinidlnaStatus(object : MinidlnaStatusCallback {
            override fun onSuccess(serviceStatus: MinidlnaStatus?) {
                minidlna.value = serviceStatus?.active ?: false
            }

            override fun onError(message: String) {
                minidlna.value = false
            }
        })
        controller.fetchTransmissionStatus(object : TransmissionStatusCallback {
            override fun onSuccess(serviceStatus: TransmissionStatus?) {
                transmission.value = serviceStatus?.active ?: false
            }

            override fun onError(message: String) {
                transmission.value = false
            }
        })
    }

    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar() },
        content = { paddingValues ->
            Row(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    StatusCard(serviceName = "Transmission", isRunning = transmission.value)
                }
                Column(modifier = Modifier.weight(1f)) {
                    StatusCard(serviceName = "Minidlna", isRunning = minidlna.value)
                }
            }
        }
    )
}