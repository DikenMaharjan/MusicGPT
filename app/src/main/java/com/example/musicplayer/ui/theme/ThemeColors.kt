package com.example.musicplayer.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
class ThemeColors(
    val primary: ColorPalette = ColorPalette(
        p50 = Color(0xFF0A0C0D),
        p100 = Color(0xFF16191C),
        p200 = Color(0xFF1D2125),
        p250 = Color(0xFF212529),
        p800 = Color(0xFF5D6165),
        p1000 = Color(0xFF777A80),
        p1100 = Color(0xFF898C92),
        p1200 = Color(0xFFBFC2C8),
        p5000 = Color(0xFFE4E6E8),
    ),
    val white: Color = Color(0xFFFFFFFF)
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