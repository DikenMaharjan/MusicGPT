package com.example.musicplayer.feature.generate_music.ui.api

import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import com.example.musicplayer.feature.generate_music.data.repo.MusicRepository
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GenerateMusicScreenStateProducer @Inject constructor(
    musicRepository: MusicRepository
) {
    val state = combine(
        musicRepository.musics,
        musicRepository.musicGenerationRecords
    ) { musics, musicGenerationRecords ->
        val sortedMusicItems = musics.map { music ->
            music.toListItem()
        }
        val sortedGeneratingItems = musicGenerationRecords
            .filter { record ->
                record.state !is MusicGenerationState.Completed || record.state.music !in musics
            }
            .map { generatingMusic ->
                generatingMusic.toListItem()
            }
        GenerateMusicScreenState(
            items = sortedGeneratingItems + sortedMusicItems
        )
    }

    private fun Music.toListItem(): GenerateMusicListItem.MusicItem {
        return GenerateMusicListItem.MusicItem(
            music = this
        )
    }

    fun MusicGenerationRecord.toListItem(): GenerateMusicListItem.GeneratingItem {
        return GenerateMusicListItem.GeneratingItem(
            musicGenerationRecord = this
        )
    }

}