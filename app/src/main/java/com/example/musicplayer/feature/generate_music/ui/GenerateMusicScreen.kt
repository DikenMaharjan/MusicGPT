package com.example.musicplayer.feature.generate_music.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicList
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicTopBar
import com.example.musicplayer.feature.generate_music.ui.component.create_music.CreateMusicView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.FloatingPlayerView
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.MusicControlActions
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui_core.modifier.removeFocusOnClick
import com.example.musicplayer.ui_core.modifier.swipe_to_dismiss.swipeToDismiss

@Composable
fun GenerateMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateMusicScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .removeFocusOnClick(),
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
            musicControlActions = MusicControlActions(
                playPrevious = viewModel::playPrevious,
                playNext = viewModel::playNext,
                pause = viewModel::pause,
                play = viewModel::play,
                dismiss = viewModel::dismissPlayer
            )
        )
    }
}

@Composable
private fun GenerateMusicContent(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState,
    generateMusic: (String) -> Unit,
    playMusic: (Music) -> Unit,
    musicControlActions: MusicControlActions
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        GenerateMusicList(
            state = state,
            playMusic = playMusic
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateMusicView(
                generateMusic = generateMusic
            )
            AnimatedContent(
                state.musicPlayerState,
                transitionSpec = {
                    slideInVertically() togetherWith slideOutVertically() using null
                },
                contentKey = { it != null }
            ) { playerState ->
                if (playerState != null) {
                    FloatingPlayerView(
                        modifier = Modifier
                            .padding(LocalSpacing.current.dimen4)
                            .swipeToDismiss(onDismiss = musicControlActions.dismiss),
                        isPlaying = playerState.isPlaying,
                        music = playerState.selectedMusic,
                        musicControlActions = musicControlActions
                    )
                }
            }
        }
    }
}