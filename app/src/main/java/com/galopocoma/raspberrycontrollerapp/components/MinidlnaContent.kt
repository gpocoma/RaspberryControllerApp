package com.galopocoma.raspberrycontrollerapp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.controllers.MinidlnaStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.StartMinidlnaCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StopMinidlnaCallback
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna
import kotlinx.coroutines.launch

@Composable
fun MinidlnaContent() {
    val showDialog = remember { mutableStateOf(false) }
    val showModal = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Estado del Servicio") },
            text = { Text(text = dialogMessage.value) },
            confirmButton = {
                Button(onClick = { showDialog.value = false }, shape = RoundedCornerShape(4.dp)) {
                    Text("Ok")
                }
            }
        )
    }
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardButton(
                        text = "Status Minidlna",
                        onClick = {
                            showModal.value = true
                            val controller = RaspberryPiController()
                            controller.fetchMinidlnaStatus(object : MinidlnaStatusCallback {
                                override fun onSuccess(minidlnaStatus: MinidlnaStatus?) {
                                    scope.launch {
                                        dialogMessage.value = if (minidlnaStatus?.active == true) {
                                            "Servicio en ejecución"
                                        } else {
                                            "Servicio detenido"
                                        }
                                        showDialog.value = true
                                        showModal.value = false
                                    }
                                }

                                override fun onError(message: String) {
                                    Log.e("Minidlna", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        },
                        icon = Icons.Default.Refresh
                    )
                    CardButton(
                        text = "Iniciar Minidlna",
                        onClick = {
                            showModal.value = true
                            val controller = RaspberryPiController()
                            controller.startMinidlna(object : StartMinidlnaCallback {
                                override fun onSuccess(startMinidlna: StartMinidlna?) {
                                    scope.launch {
                                        dialogMessage.value =
                                            startMinidlna?.message ?: "Error al iniciar el servicio"
                                        showDialog.value = true
                                        showModal.value = false
                                    }
                                }

                                override fun onError(message: String) {
                                    Log.e("Minidlna", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        },
                        icon = Icons.Default.PlayArrow
                    )
                    CardButton(
                        text = "Detener Minidlna",
                        onClick = {
                            showModal.value = true
                            val controller = RaspberryPiController()
                            controller.stopMinidlna(object : StopMinidlnaCallback {
                                override fun onSuccess(stopMinidlna: StopMinidlna?) {
                                    scope.launch {
                                        dialogMessage.value =
                                            stopMinidlna?.message ?: "Error al detener el servicio"
                                        showDialog.value = true
                                        showModal.value = false
                                    }
                                }

                                override fun onError(message: String) {
                                    Log.e("Minidlna", "Error: $message")
                                    showModal.value = true
                                }
                            })
                        },
                        icon = Icons.Default.Close
                    )
                }
                // Mostrar la animación Modal si es necesario
                if (showModal.value) {
                    ModalAnimation(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    )
                }
            }
        },
        topBar = {
            MainTopBar()
        },
        bottomBar = {
            MainBottomBar()
        }
    )
}

