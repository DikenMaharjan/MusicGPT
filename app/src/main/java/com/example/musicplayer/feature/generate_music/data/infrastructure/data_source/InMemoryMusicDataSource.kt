package com.example.musicplayer.feature.generate_music.data.infrastructure.data_source

import com.example.musicplayer.feature.generate_music.data.infrastructure.generator.MusicGenerationSession.Companion.getRandomAlbumImage
import com.example.musicplayer.feature.generate_music.data.model.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class InMemoryMusicDataSource @Inject constructor() {

    private val _musics = MutableStateFlow(initialDummyMusics)
    val musics = _musics.asStateFlow()

    fun addMusic(music: Music) {
        _musics.update { it + music }
    }

    companion object {
        private val initialDummyMusics = listOf(
            Music(
                id = "1",
                title = "Space Chill",
                prompt = "A calm beat for study or sleep, with a space feel.",
                song = 0.75,
                createdAt = Instant.now().minusSeconds(3600),
                image = getRandomAlbumImage()
            ),
            Music(
                id = "2",
                title = "Robot Battle",
                prompt = "Fast electronic music for a robot boss fight in a game.",
                song = 0.42,
                createdAt = Instant.now().minusSeconds(1800),
                image = getRandomAlbumImage()
            ),
            Music(
                id = "3",
                title = "Morning Peace",
                prompt = "A soft and calm piano song, like a quiet field in the morning.",
                song = 0.91,
                createdAt = Instant.now(),
                image = getRandomAlbumImage()
            )
        )
    }
}
