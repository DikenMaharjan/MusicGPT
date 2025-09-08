package com.example.musicplayer.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val dimen0: Dp = 0.dp,
    val dimen1: Dp = 1.dp,
    val dimen2: Dp = 2.dp,
    val dimen4: Dp = 4.dp,
    val dimen8: Dp = 8.dp,
    val dimen12: Dp = 12.dp,
    val dimen16: Dp = 16.dp,
    val dimen18: Dp = 18.dp,
    val dimen22: Dp = 22.dp,
    val dimen24: Dp = 24.dp,
    val dimen28: Dp = 28.dp,
    val dimen32: Dp = 32.dp,
    val dimen40: Dp = 40.dp,
    val dimen44: Dp = 44.dp,
    val dimen48: Dp = 48.dp,
    val dimen54: Dp = 54.dp,
    val dimen64: Dp = 64.dp,
    val dimen80: Dp = 80.dp,
    val dimen120: Dp = 120.dp,
    val dimen140: Dp = 140.dp,
    val dimen150: Dp = 150.dp,
    val dimen180: Dp = 180.dp,
    val dimen200: Dp = 200.dp,
    val dimen240: Dp = 240.dp,
    val dimen280: Dp = 280.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }