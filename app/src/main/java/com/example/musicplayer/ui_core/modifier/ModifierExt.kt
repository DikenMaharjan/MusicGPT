package com.example.musicplayer.ui_core.modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager


@Composable
fun Modifier.removeFocusOnClick(): Modifier {
    val focusManager = LocalFocusManager.current
    return this.rippleLessClickable(
        onClick = {
            focusManager.clearFocus(force = true)
        }
    )
}

@Composable
fun Modifier.rippleLessClickable(
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource? = null
): Modifier = this.clickable(
    onClick = onClick,
    interactionSource = interactionSource,
    indication = null
)

