package com.example.musicplayer.ui_core.insets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun rememberZeroWindowInsets() = remember {
    WindowInsets(0.dp)
}