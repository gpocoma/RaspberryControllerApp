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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.PostgreSQLStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StartPostgreSQLCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StopPostgreSQLCallback
import com.galopocoma.raspberrycontrollerapp.models.PostgreSQLStatus
import com.galopocoma.raspberrycontrollerapp.models.StopPostgreSQL
import com.galopocoma.raspberrycontrollerapp.models.RAMUsage
import com.galopocoma.raspberrycontrollerapp.models.StartPostgreSQL

@Composable
fun PostgreSQLContent(ramUsage: RAMUsage?) {
    val showDialog = remember { mutableStateOf(false) }
    val showModal = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val controller = remember { RaspberryPiController() }

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
                        text = "Status PostgreSQL",
                        onClick = {
                            showModal.value = true
                            controller.fetchPostgreSQLStatus(object : PostgreSQLStatusCallback {
                                override fun onSuccess(postgreSQLStatus: PostgreSQLStatus?) {
                                    dialogMessage.value = if (postgreSQLStatus?.active == true) {
                                        "Servicio en ejecución"
                                    } else {
                                        "Servicio detenido"
                                    }
                                    showDialog.value = true
                                    showModal.value = false
                                }

                                override fun onError(message: String) {
                                    Log.e("PostgreSQL", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        },
                        icon = Icons.Default.Refresh
                    )
                    CardButton(
                        text = "Iniciar PostgreSQL",
                        onClick = {
                            showModal.value = true
                            controller.startPostgreSQL(object : StartPostgreSQLCallback {
                                override fun onSuccess(startPostgreSQL: StartPostgreSQL?) {
                                    dialogMessage.value =
                                        startPostgreSQL?.message ?: "Error al iniciar el servicio"
                                    showDialog.value = true
                                    showModal.value = false
                                }

                                override fun onError(message: String) {
                                    Log.e("PostgreSQL", "Error: $message")
                                    showModal.value = false
                                }
                            })
                        },
                        icon = Icons.Default.PlayArrow
                    )
                    CardButton(
                        text = "Detener PostgreSQL",
                        onClick = {
                            showModal.value = true
                            controller.stopPostgreSQL(object : StopPostgreSQLCallback {
                                override fun onSuccess(stopPostgreSQL: StopPostgreSQL?) {
                                    dialogMessage.value =
                                        stopPostgreSQL?.message ?: "Error al detener el servicio"
                                    showDialog.value = true
                                    showModal.value = false
                                }

                                override fun onError(message: String) {
                                    Log.e("PostgreSQL", "Error: $message")
                                    showModal.value = false
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
            MainBottomBar(ramUsage)
        }
    )
}
