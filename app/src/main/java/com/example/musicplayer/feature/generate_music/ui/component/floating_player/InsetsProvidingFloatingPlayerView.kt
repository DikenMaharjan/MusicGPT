package com.example.musicplayer.feature.generate_music.ui.component.floating_player

import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui_core.components.animation.NullableValueVisibility
import com.example.musicplayer.ui_core.modifier.swipe_to_dismiss.swipeToDismiss

@Composable
fun InsetsProvidingFloatingPlayerView(
    modifier: Modifier = Modifier,
    musicControlActions: MusicControlActions,
    state: GenerateMusicScreenState
) {
    val customBottomInsets = LocalCustomBottomInsets.current
    Box(
        modifier = modifier.onGloballyPositioned { coord ->
            customBottomInsets.setLayoutPosition(coord)
        }
    ) {
        NullableValueVisibility(
            value = state.musicPlayerState,
            enter = expandVertically(expandFrom = Alignment.Top),
            exit = shrinkVertically(),
            content = { playerState ->
                FloatingPlayerView(
                    modifier = Modifier
                        .padding(LocalSpacing.current.dimen4)
                        .swipeToDismiss(onDismiss = musicControlActions.dismiss),
                    isPlaying = playerState.isPlaying,
                    music = playerState.selectedMusic,
                    musicControlActions = musicControlActions,
                    nextMusic = remember(state) { state.getNextMusic() },
                    prevMusic = remember(state) { state.getPreviousMusic() }
                )
            }
        )
    }
}