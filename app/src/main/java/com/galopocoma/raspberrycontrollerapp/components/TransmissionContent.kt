package com.galopocoma.raspberrycontrollerapp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.StartTransmissionCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StopTransmissionCallback
import com.galopocoma.raspberrycontrollerapp.controllers.TransmissionStatusCallback
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TransmissionContent() {
    val showDialog = remember { mutableStateOf(false) }
    val showModal = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Mostrar el dialogo con el estado
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
        topBar = {
            MainTopBar()
        },
        bottomBar = {
            MainBottomBar()
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CardButton(
                        text = "Status Transmission", onClick = {
                            showModal.value = true
                            val controller = RaspberryPiController()
                            controller.fetchTransmissionStatus(object : TransmissionStatusCallback {
                                override fun onSuccess(transmissionStatus: TransmissionStatus?) {
                                    scope.launch {
                                        dialogMessage.value = transmissionStatus?.active?.let {
                                            if (it) {
                                                "Servicio en ejecución"
                                            } else {
                                                "Servicio detenido"
                                            }
                                        } ?: "Error al obtener el estado del servicio"
                                        showDialog.value = true
                                        showModal.value = false
                                    }
                                }

                                override fun onError(message: String) {
                                    Log.e("Transmission", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        },
                        icon = Icons.Default.Refresh
                    )
                    CardButton(text = "Iniciar Transmission", onClick = {
                        showModal.value = true
                        val controller = RaspberryPiController()
                        controller.startTransmission(object : StartTransmissionCallback {
                            override fun onSuccess(startTransmission: StartTransmission?) {
                                scope.launch {
                                    dialogMessage.value =
                                        startTransmission?.message ?: "Error al iniciar el servicio"
                                    showDialog.value = true
                                    showModal.value = false
                                }
                            }

                            override fun onError(message: String) {
                                Log.e("Transmission", "Error: $message")
                                showModal.value = false
                            }
                        })
                    }, icon = Icons.Default.PlayArrow)
                    CardButton(
                        text = "Detener Transmission", onClick = {
                            showModal.value = true
                            val controller = RaspberryPiController()
                            controller.stopTransmission(object : StopTransmissionCallback {
                                override fun onSuccess(stopTransmission: StopTransmission?) {
                                    scope.launch {
                                        dialogMessage.value =
                                            stopTransmission?.message
                                                ?: "Error al detener el servicio"
                                        showDialog.value = true
                                        showModal.value = false
                                    }
                                }

                                override fun onError(message: String) {
                                    Log.e("Transmission", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        }, icon = Icons.Default.Close
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
        }
    )
}
