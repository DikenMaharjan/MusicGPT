package com.example.musicplayer.feature.generate_music.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState

@Composable
fun GenerateMusicList(
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