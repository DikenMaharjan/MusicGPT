package com.example.musicplayer.feature.generate_music.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.feature.generate_music.ui.component.GenerateMusicTopBar
import com.example.musicplayer.feature.generate_music.ui.component.MusicItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState

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
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(state.items) { item ->
            when (item) {
                is GenerateMusicListItem.GeneratingItem -> {
                    Text(text = item.toString())
                }

                is GenerateMusicListItem.MusicItem -> MusicItem(
                    music = item.music
                )
            }
        }
    }
}