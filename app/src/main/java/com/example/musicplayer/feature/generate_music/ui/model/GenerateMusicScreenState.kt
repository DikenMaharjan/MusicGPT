package com.example.musicplayer.feature.generate_music.ui.model

import androidx.compose.runtime.Immutable
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord

@Immutable
class GenerateMusicScreenState(
    val items: List<GenerateMusicListItem> = listOf(),
    val musicPlayerState: MusicPlayerState? = null
) {
    fun getNextMusic(): Music? {
        return getAdjacentMusic(true)
    }

    fun getPreviousMusic(): Music? {
        return getAdjacentMusic(false)
    }

    private fun getAdjacentMusic(next: Boolean): Music? {
        val currentId = musicPlayerState?.selectedMusic?.id ?: return null
        val musicItems = items.filterIsInstance<GenerateMusicListItem.MusicItem>()
        val currentIndex = musicItems.indexOfFirst { it.music.id == currentId }
        if (currentIndex == -1) return null
        val targetIndex = if (next) currentIndex + 1 else currentIndex - 1
        return musicItems.getOrNull(targetIndex)?.music
    }

}

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