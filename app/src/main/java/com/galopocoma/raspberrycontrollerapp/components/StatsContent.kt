package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.MinidlnaStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.TransmissionStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.PostgreSQLStatusCallback
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.PostgreSQLStatus


@Composable
fun StatsContent(ramUsage: RAMUsage?) {
    val controller = remember { RaspberryPiController() }
    val minidlna = remember { mutableStateOf(false) }
    val transmission = remember { mutableStateOf(false) }
    val postgresql = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        controller.fetchMinidlnaStatus(object : MinidlnaStatusCallback {
            override fun onSuccess(minidlnaStatus: MinidlnaStatus?) {
                minidlna.value = minidlnaStatus?.active ?: false
            }

            override fun onError(message: String) {
                minidlna.value = false
            }
        })
        controller.fetchTransmissionStatus(object : TransmissionStatusCallback {
            override fun onSuccess(transmissionStatus: TransmissionStatus?) {
                transmission.value = transmissionStatus?.active ?: false
            }

            override fun onError(message: String) {
                transmission.value = false
            }
        })
        controller.fetchPostgreSQLStatus(object : PostgreSQLStatusCallback {
            override fun onSuccess(postgreSQLStatus: PostgreSQLStatus?) {
                postgresql.value = postgreSQLStatus?.active ?: false
            }

            override fun onError(message: String) {
                postgresql.value = false
            }
        })
    }

    Scaffold(
        topBar = { MainTopBar() },
        bottomBar = { MainBottomBar(ramUsage) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        StatusCard(serviceName = "Transmission", isRunning = transmission.value)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        StatusCard(serviceName = "Minidlna", isRunning = minidlna.value)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        StatusCard(serviceName = "PostgreSQL", isRunning = postgresql.value)
                    }
                }
                ramUsage?.let {
                    RamUsageCard(it)
                } ?: run {
                    RamUsageCard(RAMUsage(0.0, 0.0, 0.0, 0.0))
                }
                SystemInfoCard()
            }
        }
    )
}