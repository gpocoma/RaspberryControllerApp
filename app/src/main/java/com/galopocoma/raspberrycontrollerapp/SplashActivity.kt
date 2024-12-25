package com.galopocoma.raspberrycontrollerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.galopocoma.raspberrycontrollerapp.ui.theme.RaspberryControllerAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import com.galopocoma.raspberrycontrollerapp.controllers.RaspberryPiController
import com.galopocoma.raspberrycontrollerapp.controllers.ServiceStatusCallback
import com.galopocoma.raspberrycontrollerapp.models.StatsStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaspberryControllerAppTheme {
                SplashScreen()
            }
        }

        lifecycleScope.launch {
            val isConnected = checkServerConnection()
            delay(3000) // Simula un tiempo de carga
            if (isConnected) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            } else {
                showNoConnectionDialog()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun checkServerConnection(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            val controller = RaspberryPiController()
            controller.fetchServiceStatus(object : ServiceStatusCallback {
                override fun onSuccess(serviceStatus: StatsStatus?) {
                    continuation.resume(true) {}
                }

                override fun onError(message: String) {
                    continuation.resume(false) {}
                }
            })
        }
    }

    private fun showNoConnectionDialog() {
        setContent {
            RaspberryControllerAppTheme {
                NoConnectionDialog()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Logo",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.size(128.dp)
                )
                Text(
                    text = "Verificando conexión...",
                    color = MaterialTheme.colorScheme.surface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Composable
fun NoConnectionDialog() {
    AlertDialog(
        onDismissRequest = { /* No hacer nada */ },
        title = { Text(text = "Error de Conexión") },
        text = { Text(text = "No se pudo conectar con el servidor. Por favor, inténtelo de nuevo más tarde.") },
        confirmButton = {
            Button(onClick = {
                System.exit(0)
            }) {
                Text("OK")
            }
        }
    )
}