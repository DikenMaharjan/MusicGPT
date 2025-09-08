package com.example.musicplayer.feature.generate_music.data.repo

import com.example.musicplayer.feature.generate_music.data.model.GeneratingMusic
import com.example.musicplayer.feature.generate_music.data.model.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor() {
    private val _musics = MutableStateFlow(listOf<Music>())
    val musics = _musics.asStateFlow()

    private val _generatingMusics = MutableStateFlow(listOf<GeneratingMusic>())
    val generatingMusics = _generatingMusics.asStateFlow()


}