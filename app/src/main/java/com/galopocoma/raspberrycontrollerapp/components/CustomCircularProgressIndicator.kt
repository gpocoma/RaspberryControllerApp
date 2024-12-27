package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressIndicator(modifier: Modifier = Modifier) {
    // Crear una transición infinita
    val infiniteTransition = rememberInfiniteTransition()

    // Animación de expansión y contracción
    val scale by infiniteTransition.animateFloat(
        label = "scale",
        initialValue = 1.2f,
        targetValue = 2.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Animación de rotación
    val rotation by infiniteTransition.animateFloat(
        label = "rotation",
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Animación de cambio de color
    val color by infiniteTransition.animateColor(
        label = "color",
        initialValue = MaterialTheme.colorScheme.primary,
        targetValue = MaterialTheme.colorScheme.surface,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    CircularProgressIndicator(
        modifier = Modifier
            .scale(scale) // Aplicar la animación de escala
            .rotate(rotation), // Aplicar la rotación
        color = color, // Aplicar el cambio de color
        strokeWidth = 4.dp // Tamaño del borde
    )
}