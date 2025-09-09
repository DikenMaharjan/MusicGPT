package com.example.musicplayer.feature.generate_music.data.infrastructure.data_source

import com.example.musicplayer.feature.generate_music.data.model.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InMemoryMusicDataSource @Inject constructor() {

    private val _musics = MutableStateFlow(listOf<Music>())
    val musics = _musics.asStateFlow()

    fun addMusic(music: Music) {
        _musics.update { it + music }
    }
}
