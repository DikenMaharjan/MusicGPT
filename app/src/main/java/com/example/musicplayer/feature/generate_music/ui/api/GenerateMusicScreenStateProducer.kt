package com.example.musicplayer.feature.generate_music.ui.api

import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import com.example.musicplayer.feature.generate_music.data.repo.MusicRepository
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.feature.generate_music.ui.model.MusicPlayerState
import com.example.musicplayer.feature.music_player.MusicController
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
            ?.takeIf {
                musicPlayerState.playing
            }
        val sortedMusicItems = musics.map { music ->
            music.toListItem(isPlaying = currentlyPlaying?.id == music.id)
        }
        val sortedGeneratingItems = musicGenerationRecords.getGeneratingItems(musics)
        GenerateMusicScreenState(
            items = sortedGeneratingItems + sortedMusicItems,
            musicPlayerState = musics
                .firstOrNull { it.id == musicPlayerState.selectedContent?.id }
                ?.let {
                    MusicPlayerState(
                        selectedMusic = it,
                        isPlaying = musicPlayerState.playing
                    )
                }
        )
    }

    private fun List<MusicGenerationRecord>.getGeneratingItems(
        musics: List<Music>
    ): List<GenerateMusicListItem.GeneratingItem> = filter { record ->
        record.state !is MusicGenerationState.Completed || record.state.music !in musics
    }.map { generatingMusic ->
        generatingMusic.toListItem()
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