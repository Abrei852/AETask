package com.ae.aetask.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = primaryLight,
    onPrimary = onPrimaryLight,
    background = backgroundLight,
    secondary = secondaryLight,
    tertiary = tertiaryLight,
    onSurface = onSurfaceLight
)

private val DarkColorScheme = darkColorScheme(

    primary = primaryDark,
    onPrimary = onPrimaryDark,
    background = backgroundDark,
    secondary = secondaryDark,
    tertiary = tertiaryDark,
    onSurface = onSurfaceDark
)

@Composable
fun AETaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}