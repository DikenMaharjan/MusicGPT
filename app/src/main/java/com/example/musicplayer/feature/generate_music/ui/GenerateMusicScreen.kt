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
import com.example.musicplayer.feature.generate_music.ui.component.CreateMusicButton
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicList
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicTopBar
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.ui.theme.LocalSpacing

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
            state = state
        )
    }
}

@Composable
private fun GenerateMusicContent(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState
) {
    Box(modifier = modifier.fillMaxSize()) {
        GenerateMusicList(
            state = state
        )
        CreateMusicButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
//                .padding(LocalSpacing.current.dimen4)
        )
    }
}