package com.galopocoma.raspberrycontrollerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.galopocoma.raspberrycontrollerapp.ui.theme.RaspberryControllerAppTheme
import com.galopocoma.raspberrycontrollerapp.components.GeneralButton
import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.galopocoma.raspberrycontrollerapp.controllers.ServiceStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.TransmissionStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StartTransmissionCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StopTransmissionCallback
import com.galopocoma.raspberrycontrollerapp.controllers.MinidlnaStatusCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StartMinidlnaCallback
import com.galopocoma.raspberrycontrollerapp.controllers.StopMinidlnaCallback

import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import com.galopocoma.raspberrycontrollerapp.models.TransmissionStatus
import com.galopocoma.raspberrycontrollerapp.models.StartTransmission
import com.galopocoma.raspberrycontrollerapp.models.StopTransmission
import com.galopocoma.raspberrycontrollerapp.models.MinidlnaStatus
import com.galopocoma.raspberrycontrollerapp.models.StartMinidlna
import com.galopocoma.raspberrycontrollerapp.models.StopMinidlna
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RaspberryControllerAppTheme {
                MainContent()
            }
        }
    }
}

enum class Screen {
    Default, Transmission, Minidlna, Stats
}


@Composable
fun MainContent() {
    Surface {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val selectedScreen = remember { mutableStateOf<Screen>(Screen.Default) }
        val scope = rememberCoroutineScope()
        ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Raspberry Controller App",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Transmission") },
                    selected = selectedScreen.value == Screen.Transmission,
                    onClick = {
                        selectedScreen.value = Screen.Transmission
                        scope.launch { drawerState.close() }
                    },
                    shape = RoundedCornerShape(4.dp)

                )
                NavigationDrawerItem(
                    label = { Text("MiniDLNA") },
                    selected = selectedScreen.value == Screen.Minidlna,
                    onClick = {
                        selectedScreen.value = Screen.Minidlna
                        scope.launch { drawerState.close() }
                    },
                    shape = RoundedCornerShape(4.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Stats") },
                    selected = selectedScreen.value == Screen.Stats,
                    onClick = {
                        selectedScreen.value = Screen.Stats
                        scope.launch { drawerState.close() }
                    },
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }) {
            when (selectedScreen.value) {
                Screen.Default -> DefaultMainScreen()
                Screen.Transmission -> TransmissionContent()
                Screen.Minidlna -> MinidlnaContent()
                Screen.Stats -> StatsContent()
            }
        }
    }
}

@Composable
fun DefaultMainScreen() {
    Scaffold(topBar = {
        MainTopBar()
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Contenido vacío
        }
    })
}

@Composable
fun TransmissionContent() {
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
    Scaffold(content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GeneralButton(text = "Status Transmission", onClick = {
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
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Transmission", "Error: $message")
                    }
                })
            })
            GeneralButton(text = "Iniciar Transmission", onClick = {
                val controller = RaspberryPiController()
                controller.startTransmission(object : StartTransmissionCallback {
                    override fun onSuccess(startTransmission: StartTransmission?) {
                        scope.launch {
                            dialogMessage.value =
                                startTransmission?.message ?: "Error al iniciar el servicio"
                            showDialog.value = true
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Transmission", "Error: $message")
                    }
                })
            })
            GeneralButton(text = "Detener Transmission", onClick = {
                val controller = RaspberryPiController()
                controller.stopTransmission(object : StopTransmissionCallback {
                    override fun onSuccess(stopTransmission: StopTransmission?) {
                        scope.launch {
                            dialogMessage.value =
                                stopTransmission?.message ?: "Error al detener el servicio"
                            showDialog.value = true
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Transmission", "Error: $message")
                    }
                })
            })
        }
    }, topBar = {
        MainTopBar()
    })
}

@Composable
fun MinidlnaContent() {
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
    Scaffold(content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GeneralButton(text = "Status Minidlna", onClick = {
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
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Minidlna", "Error: $message")
                    }
                })
            })
            GeneralButton(text = "Iniciar Minidlna", onClick = {
                val controller = RaspberryPiController()
                controller.startMinidlna(object : StartMinidlnaCallback {
                    override fun onSuccess(startMinidlna: StartMinidlna?) {
                        scope.launch {
                            dialogMessage.value =
                                startMinidlna?.message ?: "Error al iniciar el servicio"
                            showDialog.value = true
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Minidlna", "Error: $message")
                    }
                })
            })
            GeneralButton(text = "Detener Minidlna", onClick = {
                val controller = RaspberryPiController()
                controller.stopMinidlna(object : StopMinidlnaCallback {
                    override fun onSuccess(stopMinidlna: StopMinidlna?) {
                        scope.launch {
                            dialogMessage.value =
                                stopMinidlna?.message ?: "Error al detener el servicio"
                            showDialog.value = true
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Minidlna", "Error: $message")
                    }
                })
            })
        }
    }, topBar = {
        MainTopBar()
    })
}

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

    Scaffold(content = { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GeneralButton(text = "Verificar estado del servicio", onClick = {
                val controller = RaspberryPiController()
                controller.fetchServiceStatus(object : ServiceStatusCallback {
                    override fun onSuccess(serviceStatus: StatsStatus?) {
                        scope.launch {
                            dialogMessage.value =
                                serviceStatus?.message ?: "Error al obtener el estado del servicio"
                            showDialog.value = true
                        }
                    }

                    override fun onError(message: String) {
                        Log.e("Stats", "Error: $message")
                    }
                })
            })
        }
    }, topBar = {
        MainTopBar()
    })
}


@Composable
fun MainTopBar() {
    Column {
        Spacer(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .statusBarsPadding()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Raspberry Controller App",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}