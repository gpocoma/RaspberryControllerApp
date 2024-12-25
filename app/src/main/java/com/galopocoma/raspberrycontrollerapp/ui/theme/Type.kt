package com.galopocoma.raspberrycontrollerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.galopocoma.raspberrycontrollerapp.R

val UniNeue = FontFamily(
    Font(R.font.unineue_regular, FontWeight.Normal),
    Font(R.font.unineue_bold, FontWeight.Bold)
)

val LightTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    ),
    titleLarge = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),
    titleMedium = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    )
)

val DarkTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.White
    ),
    titleLarge = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color.White
    ),
    titleMedium = TextStyle(
        fontFamily = UniNeue,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = Color.White
    )
)

