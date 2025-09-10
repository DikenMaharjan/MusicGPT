package com.example.musicplayer.feature.generate_music.ui.model

import androidx.compose.runtime.Immutable
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord

@Immutable
class GenerateMusicScreenState(
    val items: List<GenerateMusicListItem> = listOf()
)


sealed class GenerateMusicListItem {
    @Immutable
    data class MusicItem(
        val music: Music
    ) : GenerateMusicListItem()

    @Immutable
    data class GeneratingItem(
        val musicGenerationRecord: MusicGenerationRecord
    ) : GenerateMusicListItem()
}