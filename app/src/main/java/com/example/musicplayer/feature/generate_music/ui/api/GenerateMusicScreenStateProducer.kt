package com.example.musicplayer.feature.generate_music.ui.api

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
        musicRepository.generatingMusics
    ) { musics, generatingMusics ->
        val sortedMusicItems = musics.map { music ->
            GenerateMusicListItem.MusicItem(
                music = music
            )
        }
        val sortedGeneratingItems = generatingMusics.map { generatingMusic ->
            GenerateMusicListItem.GeneratingItem(
                generatingMusic = generatingMusic
            )
        }
        GenerateMusicScreenState(
            items = sortedGeneratingItems + sortedMusicItems
        )
    }
}