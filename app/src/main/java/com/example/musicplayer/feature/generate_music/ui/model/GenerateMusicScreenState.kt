package com.example.musicplayer.feature.generate_music.ui.model

import androidx.compose.runtime.Immutable
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord

@Immutable
class GenerateMusicScreenState(
    val items: List<GenerateMusicListItem> = listOf(),
    val musicPlayerState: MusicPlayerState? = null
)

@Immutable
data class MusicPlayerState(
    val selectedMusic: Music,
    val isPlaying: Boolean
)

sealed class GenerateMusicListItem {
    @Immutable
    data class MusicItem(
        val music: Music,
        val isPlaying: Boolean
    ) : GenerateMusicListItem()

    @Immutable
    data class GeneratingItem(
        val musicGenerationRecord: MusicGenerationRecord
    ) : GenerateMusicListItem()
}