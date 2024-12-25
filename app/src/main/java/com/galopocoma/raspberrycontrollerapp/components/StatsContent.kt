package com.galopocoma.raspberrycontrollerapp.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.components.GeneralButton
import com.galopocoma.raspberrycontrollerapp.components.MainTopBar
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.ServiceStatusCallback
import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import kotlinx.coroutines.launch

@Composable
fun StatsContent() {
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Estado del Servicio") },
            text = { Text(text = dialogMessage.value) },
            confirmButton = {
                Button(onClick = { showDialog.value = false }, shape = RoundedCornerShape(4.dp)) {
                    Text("OK")
                }
            }
        )
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CardButton(text = "Verificar estado del servicio", onClick = {
                    val controller = RaspberryPiController()
                    controller.fetchServiceStatus(object : ServiceStatusCallback {
                        override fun onSuccess(serviceStatus: StatsStatus?) {
                            scope.launch {
                                dialogMessage.value =
                                    serviceStatus?.message
                                        ?: "Error al obtener el estado del servicio"
                                showDialog.value = true
                            }
                        }

                        override fun onError(message: String) {
                            Log.e("Stats", "Error: $message")
                        }
                    })
                }, icon = Icons.Default.Refresh)
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