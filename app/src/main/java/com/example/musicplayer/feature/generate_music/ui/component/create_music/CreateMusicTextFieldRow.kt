package com.example.musicplayer.feature.generate_music.ui.component.create_music

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.dropWhile

@Composable
fun CreateMusicTextFieldRow(
    modifier: Modifier = Modifier,
    onFocusRemoved: () -> Unit
) {
    val onRemoved by rememberUpdatedState(onFocusRemoved)
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    LaunchedEffect(Unit) {
        snapshotFlow { isFocused }
            // Drop the state when the text field is not focused during composition
            .dropWhile { isFocused -> !isFocused }
            .collect { isFocused ->
                if (!isFocused) {
                    onRemoved()
                }
            }
    }
    OutlinedTextField(
        modifier = modifier
            .imePadding(),
        interactionSource = interactionSource,
        value = "",
        onValueChange = {},
    )
}