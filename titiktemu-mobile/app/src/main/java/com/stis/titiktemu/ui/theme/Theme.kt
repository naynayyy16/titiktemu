package com.stis.titiktemu.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Surface,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = PrimaryDark,
    secondary = Primary,
    tertiary = Primary,
    background = Background,
    surface = Surface,
    error = DangerRed,
    onError = OnError,
    errorContainer = ErrorContainer
)

@Composable
fun TitikTemuTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    androidx.compose.material3.MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
