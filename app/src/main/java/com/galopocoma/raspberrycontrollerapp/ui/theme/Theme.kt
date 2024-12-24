package com.galopocoma.raspberrycontrollerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xFF286140), // Pantone 1
    secondary = Color(0xFF6f263d), // Pantone 2
    tertiary = Color(0xFFb9975b), // Pantone 3
    surface = Color(0xFF97d700), // Pantone 4
    background = Color(0xFFEEEEEE)
)

private val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF286140), // Pantone 1
    secondary = Color(0xFF6f263d), // Pantone 2
    tertiary = Color(0xFFb9975b), // Pantone 3
    surface = Color(0xFF97d700), // Pantone 4
    background = Color(0xFFEEEEEE)

    /* Other default colors to override
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun RaspberryControllerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColorScheme else CustomLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}