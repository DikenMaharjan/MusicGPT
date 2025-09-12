package com.example.musicplayer.feature.generate_music.ui

import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicList
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicTopBar
import com.example.musicplayer.feature.generate_music.ui.component.create_music.CreateMusicView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.FloatingPlayerView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.LocalFloatingPlayerInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.MusicControlActions
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.floatingPlayerInsets
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui_core.components.animation.NullableValueVisibility
import com.example.musicplayer.ui_core.components.tap_to_remove_focus.TapToRemoveFocusLayout
import com.example.musicplayer.ui_core.modifier.swipe_to_dismiss.swipeToDismiss

@Composable
fun GenerateMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateMusicScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TapToRemoveFocusLayout(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                GenerateMusicTopBar()
            },
        ) { padding ->
            GenerateMusicContent(
                modifier = Modifier
                    .padding(padding),
                state = state,
                generateMusic = viewModel::generateMusic,
                playMusic = viewModel::play,
                retryGeneration = viewModel::retryGeneration,
                musicControlActions = MusicControlActions(
                    pause = viewModel::pause,
                    play = viewModel::play,
                    dismiss = viewModel::dismissPlayer,
                    playMusic = viewModel::play
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenerateMusicContent(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState,
    generateMusic: (String) -> Unit,
    playMusic: (Music) -> Unit,
    retryGeneration: (MusicGenerationRecord) -> Unit,
    musicControlActions: MusicControlActions
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        GenerateMusicList(
            state = state,
            playMusic = playMusic,
            retryGeneration = retryGeneration
        )
        CreateMusicView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .floatingPlayerInsets(),
            generateMusic = generateMusic
        )
        val localFloatingPlayerInsets = LocalFloatingPlayerInsets.current
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned { coord ->
                    localFloatingPlayerInsets.setPlayerPosition(coord)
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
}