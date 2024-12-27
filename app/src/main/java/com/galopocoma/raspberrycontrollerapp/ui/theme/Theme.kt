package com.galopocoma.raspberrycontrollerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xFF286140),
    secondary = Color(0xFF6f263d),
    tertiary = Color(0xFFb9975b),
    surface = Color(0xFF97d700),
    background = Color.Black,

    onPrimary =Color(0xFFEEEEEE),
    onSecondary = Color(0xFFEEEEEE),
    onTertiary = Color(0xFF286140),
    onSurface = Color(0xFFEEEEEE),
    onBackground = Color(0xFF4A8C6F)
)

private val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF286140),
    secondary = Color(0xFF6f263d),
    tertiary = Color(0xFFb9975b),
    surface = Color(0xFF97d700),
    background = Color(0xFFEEEEEE),

    onPrimary = Color(0xFFEEEEEE),
    onSecondary = Color(0xFFEEEEEE),
    onTertiary = Color(0xFF286140),
    onSurface = Color(0xFFEEEEEE),
    onBackground = Color(0xFF4A8C6F)
)

@Composable
fun RaspberryControllerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColorScheme else CustomLightColorScheme
    val typography = if (darkTheme) DarkTypography else LightTypography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}