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
import com.galopocoma.raspberrycontrollerapp.components.MainTopBar
import com.galopocoma.raspberrycontrollerapp.components.DefaultMainScreen
import com.galopocoma.raspberrycontrollerapp.components.StatsContent
import com.galopocoma.raspberrycontrollerapp.components.TransmissionContent
import com.galopocoma.raspberrycontrollerapp.components.MinidlnaContent

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

