package com.example.musicplayer.ui_core.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

inline fun Modifier.optional(
    enabled: Boolean, factory:
    Modifier.() -> Modifier
): Modifier {
    return if (enabled) {
        this.factory()
    } else {
        this
    }
}

@Composable
fun Modifier.rippleLessClickable(
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource? = null
): Modifier = this.clickable(
    onClick = onClick,
    interactionSource = interactionSource
        ?: remember { MutableInteractionSource() }, // Provide a default if null
    indication = null
)
