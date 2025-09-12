package com.example.musicplayer.ui_core.components.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.ProvideFloatingPlayerInsets
import com.example.musicplayer.ui_core.components.tap_to_remove_focus.TapToRemoveFocusLayout

@Composable
fun AppContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TapToRemoveFocusLayout(
        modifier = modifier
            .fillMaxSize(),
    ) {
        ProvideFloatingPlayerInsets(
            content = content
        )
    }
}