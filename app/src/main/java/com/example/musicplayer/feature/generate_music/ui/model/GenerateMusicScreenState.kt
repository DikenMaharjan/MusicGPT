package com.example.musicplayer.feature.generate_music.ui.model

import androidx.compose.runtime.Immutable
import com.example.musicplayer.feature.generate_music.data.model.GeneratingMusic
import com.example.musicplayer.feature.generate_music.data.model.Music

@Immutable
class GenerateMusicScreenState(
    val items: List<GenerateMusicListItem> = listOf()
)


sealed class GenerateMusicListItem {
    @Immutable
    class MusicItem(
        val music: Music
    ) : GenerateMusicListItem()

    @Immutable
    class GeneratingItem(
        val generatingMusic: GeneratingMusic
    ) : GenerateMusicListItem()
}