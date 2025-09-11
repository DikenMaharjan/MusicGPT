package com.example.musicplayer.feature.generate_music.data.repo

import com.example.musicplayer.feature.generate_music.data.infrastructure.data_source.InMemoryMusicDataSource
import com.example.musicplayer.feature.generate_music.data.infrastructure.generator.MusicGenerationSession
import com.example.musicplayer.feature.generate_music.data.infrastructure.generator.MusicGenerationSessionFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicRepository @Inject constructor(
    inMemoryMusicDataSource: InMemoryMusicDataSource,
    private val musicGenerationSessionFactory: MusicGenerationSessionFactory,
) {

    val musics = inMemoryMusicDataSource.musics
    private val _musicGenerationSessions = MutableStateFlow(listOf<MusicGenerationSession>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val musicGenerationRecords = _musicGenerationSessions.flatMapLatest { sessions ->
        val records = sessions.map {
            it.musicGenerationRecord
        }
        if (records.isEmpty()) {
            flowOf(listOf())
        } else {
            combine(records) { musicsBeingGenerated ->
                musicsBeingGenerated.toList()
            }
        }
    }

    fun generateMusic(prompt: String) {
        val session = musicGenerationSessionFactory.create(prompt)
        session.start()
        _musicGenerationSessions.update { initial ->
            initial + session
        }
    }

}