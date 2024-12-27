package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.R

import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.SystemShutdownCallback
import com.galopocoma.raspberrycontrollerapp.models.SystemShutdown

@Composable
fun MainTopBar() {
    var expanded by remember { mutableStateOf(false) }
    val controller = remember { RaspberryPiController() }
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }
    val showConfirmationDialog = remember { mutableStateOf(false) }

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

    if (showConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog.value = false },
            title = { Text(text = "Confirmación") },
            text = { Text(text = "¿Estás seguro de que deseas apagar la Raspberry?") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Menu",
                    tint = Color.Black
                )
            },
            containerColor = MaterialTheme.colorScheme.background,
            confirmButton = {
                Button(onClick = {
                    controller.shutdown(object : SystemShutdownCallback {
                        override fun onSuccess(systemShutdown: SystemShutdown) {
                            dialogMessage.value = systemShutdown.message
                            showDialog.value = true
                        }

                        override fun onError(message: String) {
                            dialogMessage.value = "Algo salió mal"
                            showDialog.value = true
                        }
                    })
                    showConfirmationDialog.value = false
                    expanded = false
                }, shape = RoundedCornerShape(4.dp)) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmationDialog.value = false },
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column {
        Spacer(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .statusBarsPadding()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(48.dp)
                )
                Text(
                    text = "Raspberry Controller App",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                Box {
                    IconButton(
                        onClick = { expanded = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Apagar Raspberry",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            },
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary),
                            onClick = {
                                showConfirmationDialog.value = true
                            }
                        )
                    }
                }
            }
        }
    }
}