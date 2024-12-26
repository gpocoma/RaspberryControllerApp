package com.galopocoma.raspberrycontrollerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.galopocoma.raspberrycontrollerapp.components.MinidlnaContent
import com.galopocoma.raspberrycontrollerapp.components.StatsContent
import com.galopocoma.raspberrycontrollerapp.components.TransmissionContent
import com.galopocoma.raspberrycontrollerapp.ui.theme.RaspberryControllerAppTheme
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
    Stats, Transmission, Minidlna
}


@Composable
fun MainContent() {
    Surface {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val selectedScreen = remember { mutableStateOf<Screen>(Screen.Stats) }
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
                    label = { Text("Home") },
                    selected = selectedScreen.value == Screen.Stats,
                    onClick = {
                        selectedScreen.value = Screen.Stats
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
                    label = { Text("Transmission") },
                    selected = selectedScreen.value == Screen.Transmission,
                    onClick = {
                        selectedScreen.value = Screen.Transmission
                        scope.launch { drawerState.close() }
                    },
                    shape = RoundedCornerShape(4.dp)
                )
            }
        }) {
            when (selectedScreen.value) {
                Screen.Stats -> StatsContent()
                Screen.Transmission -> TransmissionContent()
                Screen.Minidlna -> MinidlnaContent()
            }
        }
    }
}

