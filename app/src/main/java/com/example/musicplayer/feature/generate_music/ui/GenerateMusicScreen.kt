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
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.MusicControlActions
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.ProvideCustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.customBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.rememberCustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui_core.components.animation.NullableValueVisibility
import com.example.musicplayer.ui_core.modifier.swipe_to_dismiss.swipeToDismiss

@Composable
fun GenerateMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateMusicScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
            delete = viewModel::delete,
            regenerate = viewModel::regenerate,
            musicControlActions = MusicControlActions(
                pause = viewModel::pause,
                play = viewModel::play,
                dismiss = viewModel::dismissPlayer,
                playMusic = viewModel::play
            )
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenerateMusicContent(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState,
    generateMusic: (String) -> Unit,
    playMusic: (Music) -> Unit,
    delete: (Music) -> Unit,
    regenerate: (Music) -> Unit,
    retryGeneration: (MusicGenerationRecord) -> Unit,
    musicControlActions: MusicControlActions
) {
    val createMusicViewInsets = rememberCustomBottomInsets()
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ProvideCustomBottomInsets(
            customBottomInsets = createMusicViewInsets
        ) {
            GenerateMusicList(
                state = state,
                playMusic = playMusic,
                retryGeneration = retryGeneration,
                delete = delete,
                regenerate = regenerate
            )
        }
        val floatingPlayerInsets = rememberCustomBottomInsets()
        CreateMusicView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .customBottomInsets(floatingPlayerInsets)
                .onGloballyPositioned { coord ->
                    createMusicViewInsets.setLayoutPosition(coord)
                },
            generateMusic = generateMusic
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned { coord ->
                    floatingPlayerInsets.setLayoutPosition(coord)
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