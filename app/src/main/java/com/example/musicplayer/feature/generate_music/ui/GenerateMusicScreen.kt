package com.example.musicplayer.feature.generate_music.ui

import androidx.compose.foundation.layout.Box
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
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui_core.modifier.removeFocusOnClick

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
            playMusic = viewModel::play
        )
    }
}

@Composable
private fun GenerateMusicContent(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState,
    generateMusic: (String) -> Unit,
    playMusic: (Music) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        GenerateMusicList(
            state = state,
            playMusic = playMusic
        )
        CreateMusicView(
            modifier = Modifier.align(Alignment.BottomCenter),
            generateMusic = generateMusic
        )
    }
}