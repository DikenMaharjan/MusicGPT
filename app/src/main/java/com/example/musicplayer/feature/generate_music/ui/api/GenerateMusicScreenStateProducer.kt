package com.example.musicplayer.feature.generate_music.ui.api

import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.repo.MusicRepository
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.feature.generate_music.ui.model.MusicPlayerState
import com.example.musicplayer.feature.music_player.MusicController
import com.example.musicplayer.feature.music_player.model.MusicControllerState
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GenerateMusicScreenStateProducer @Inject constructor(
    musicRepository: MusicRepository,
    musicController: MusicController
) {
    val state = combine(
        musicRepository.musics,
        musicRepository.musicGenerationRecords,
        musicController.musicPlayerState
    ) { musics, musicGenerationRecords, musicPlayerState ->
        val currentlyPlaying = musicPlayerState
            .selectedContent
            ?.takeIf { musicPlayerState.playing }

        val musicItems = musics
            .map { music -> music.toListItem(isPlaying = currentlyPlaying?.id == music.id) }

        val generatingItems = musicGenerationRecords
            .values
            .toList()
            .getGeneratingItems()

        GenerateMusicScreenState(
            items = (musicItems + generatingItems)
                .distinctBy { it.id }
                .sortedByDescending { it.createdAt },
            musicPlayerState = musics.getMusicPlayerContent(musicPlayerState)
        )
    }

    private fun List<MusicGenerationRecord>.getGeneratingItems() = map { generatingMusic ->
        generatingMusic.toListItem()
    }

    private fun List<Music>.getMusicPlayerContent(musicControllerState: MusicControllerState): MusicPlayerState? {
        return firstOrNull { it.id == musicControllerState.selectedContent?.id }
            ?.let { selectedMusic ->
                MusicPlayerState(
                    selectedMusic = selectedMusic,
                    isPlaying = musicControllerState.playing
                )
            }
    }

    private fun Music.toListItem(isPlaying: Boolean): GenerateMusicListItem.MusicItem {
        return GenerateMusicListItem.MusicItem(
            music = this,
            isPlaying = isPlaying
        )
    }

    fun MusicGenerationRecord.toListItem(): GenerateMusicListItem.GeneratingItem {
        return GenerateMusicListItem.GeneratingItem(
            musicGenerationRecord = this
        )
    }

}