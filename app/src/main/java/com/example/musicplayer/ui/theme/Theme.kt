package com.example.musicplayer.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val appScheme = darkColorScheme(
    primary = LogoOrange,
    background = Primary50,
    surface = Primary50,
    onSurface = Primary5000,
    onBackground = Primary5000
)

@Composable
fun MusicPlayerTheme(
    themeColors: ThemeColors = LocalThemeColor.current,
    spacing: Spacing = LocalSpacing.current,
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(
        LocalThemeColor provides themeColors,
        LocalSpacing provides spacing,
    ) {
        MaterialTheme(
            colorScheme = appScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
fun PreviewTheme(
    content: @Composable () -> Unit
) {
    MusicPlayerTheme(
        content = {
            Surface { content() }
        }
    )
}