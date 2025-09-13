package com.example.musicplayer.feature.generate_music.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicTopBar
import com.example.musicplayer.feature.generate_music.ui.component.create_music.CreateMusicView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.InsetsProvidingFloatingPlayerView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.LocalCustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.MusicControlActions
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.ProvideCustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.customBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.rememberCustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.music_list.GenerateMusicList
import com.example.musicplayer.feature.generate_music.ui.component.music_list.NoMusicView
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui_core.haze.LocalHazeSource
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

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
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        val hazeState = rememberHazeState()
        val floatingPlayerInsets = rememberCustomBottomInsets()

        CompositionLocalProvider(
            LocalHazeSource provides hazeState,
            LocalCustomBottomInsets provides floatingPlayerInsets
        ) {
            val createMusicViewInsets = rememberCustomBottomInsets()
            ProvideCustomBottomInsets(
                customBottomInsets = createMusicViewInsets
            ) {
                if (state.items.isEmpty()) {
                    NoMusicView(
                        modifier = modifier
                            .fillMaxSize()
                            .customBottomInsets(createMusicViewInsets),
                    )
                } else {
                    GenerateMusicList(
                        modifier = Modifier.hazeSource(hazeState),
                        state = state,
                        playMusic = playMusic,
                        retryGeneration = retryGeneration,
                        delete = delete,
                        regenerate = regenerate
                    )
                }
            }
            CreateMusicView(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .onGloballyPositioned { coord ->
                        createMusicViewInsets.setLayoutPosition(coord)
                    },
                generateMusic = generateMusic
            )
            InsetsProvidingFloatingPlayerView(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                state = state,
                musicControlActions = musicControlActions
            )
        }
    }
}