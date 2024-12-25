package com.galopocoma.raspberrycontrollerapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.galopocoma.raspberrycontrollerapp.ui.theme.RaspberryControllerAppTheme

@Composable
fun ModalAnimation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CustomCircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ModalAnimationPreview() {
    RaspberryControllerAppTheme {
        ModalAnimation()
    }
}
