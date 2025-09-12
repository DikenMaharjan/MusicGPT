package com.example.musicplayer.feature.generate_music.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.ui.component.floating_player.CustomBottomInsets
import com.example.musicplayer.feature.generate_music.ui.component.music_item.MusicGenerationItem
import com.example.musicplayer.feature.generate_music.ui.component.music_item.MusicItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenerateMusicList(
    modifier: Modifier = Modifier,
    state: GenerateMusicScreenState,
    playMusic: (Music) -> Unit,
    retryGeneration: (MusicGenerationRecord) -> Unit
) {
    val remainingInsets = remember { MutableWindowInsets() }
    val bottomInset = CustomBottomInsets.BottomInsets
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .onConsumedWindowInsetsChanged { consumedWindowInsets ->
                remainingInsets.insets = bottomInset.exclude(consumedWindowInsets)
            },
        contentPadding = remainingInsets.asPaddingValues()
    ) {
        items(state.items) { item ->
            when (item) {
                is GenerateMusicListItem.GeneratingItem -> {
                    MusicGenerationItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        generationRecord = item.musicGenerationRecord,
                        retryGeneration = retryGeneration
                    )
                }

                is GenerateMusicListItem.MusicItem -> {
                    MusicItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { playMusic(item.music) },
                        musicItem = item
                    )
                }
            }
        }
    }
}