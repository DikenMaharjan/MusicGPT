package com.example.musicplayer.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


@Immutable
class ThemeColors(
    val primary: ColorPalette = ColorPalette(
        p50 = Primary50,
        p100 = Primary100,
        p200 = Primary200,
        p250 = Primary250,
        p800 = Primary800,
        p1000 = Primary1000,
        p1100 = Primary1100,
        p1200 = Primary1200,
        p5000 = Primary5000,
    ),
    val white: Color = White
)

@Immutable
data class ColorPalette(
    val p50: Color,
    val p100: Color,
    val p200: Color,
    val p250: Color,
    val p800: Color,
    val p1000: Color,
    val p1100: Color,
    val p1200: Color,
    val p5000: Color,
)

val LocalThemeColor = staticCompositionLocalOf {
    ThemeColors()
}