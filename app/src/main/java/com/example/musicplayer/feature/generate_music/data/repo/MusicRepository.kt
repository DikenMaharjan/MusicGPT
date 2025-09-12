package com.example.musicplayer.feature.generate_music.data.repo

import com.example.musicplayer.feature.generate_music.data.infrastructure.data_source.InMemoryMusicDataSource
import com.example.musicplayer.feature.generate_music.data.infrastructure.data_source.InMemoryMusicGenerationRecords
import com.example.musicplayer.feature.generate_music.data.infrastructure.generator.MusicGenerationUpdate
import com.example.musicplayer.feature.generate_music.data.infrastructure.generator.MusicGenerator
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import com.example.musicplayer.utils.coroutine.AppScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MusicRepository @Inject constructor(
    private val inMemoryMusicDataSource: InMemoryMusicDataSource,
    private val inMemoryMusicGenerationRecords: InMemoryMusicGenerationRecords,
    private val appScope: AppScope,
    private val musicGenerator: MusicGenerator
) {

    val musics = inMemoryMusicDataSource.musics

    val musicGenerationRecords = inMemoryMusicGenerationRecords.musicGenerationRecords

    fun generateMusic(prompt: String) {
        val id = UUID.randomUUID().toString()
        val generationRecord = MusicGenerationRecord(
            id = id,
            prompt = prompt
        )
        generateMusic(generationRecord)
    }

    private fun generateMusic(
        generationRecord: MusicGenerationRecord
    ) {
        appScope.launch {
            inMemoryMusicGenerationRecords.upsert(generationRecord)
            musicGenerator
                .generate(prompt = generationRecord.prompt)
                .catch { error ->
                    handleMusicGenerationError(
                        generationRecord = generationRecord,
                        error = error
                    )
                }
                .collect { update ->
                    handleStateUpdates(
                        generationRecord = generationRecord,
                        update = update
                    )
                }
        }

    }

    private fun handleMusicGenerationError(
        generationRecord: MusicGenerationRecord,
        error: Throwable
    ) {
        inMemoryMusicGenerationRecords.upsert(
            musicGenerationRecord = generationRecord.copy(
                progress = 0f,
                state = MusicGenerationState.Failed(
                    errorMessage = error.message ?: "Generation Failed"
                )
            )
        )
    }

    private fun handleStateUpdates(
        generationRecord: MusicGenerationRecord,
        update: MusicGenerationUpdate
    ) {
        val state = update.state
        if (state is MusicGenerationState.Completed) {
            val generatedMusic = generationRecord.toMusic(state)
            inMemoryMusicDataSource.addMusic(generatedMusic)
            inMemoryMusicGenerationRecords.delete(generationRecord)
        } else {
            inMemoryMusicGenerationRecords.upsert(
                musicGenerationRecord = generationRecord.copy(
                    progress = update.progress,
                    state = update.state
                )
            )
        }
    }

    private fun MusicGenerationRecord.toMusic(completedState: MusicGenerationState.Completed): Music {
        return Music(
            title = completedState.title,
            prompt = prompt,
            song = completedState.song,
            image = completedState.image,
            createdAt = completedState.createdAt,
            id = id,
            version = version
        )
    }

    fun retryGeneration(musicGenerationRecord: MusicGenerationRecord) {
        generateMusic(
            generationRecord = musicGenerationRecord.copy(
                version = musicGenerationRecord.version + 1,
                state = MusicGenerationState.Initializing
            )
        )
    }

    fun delete(music: Music) {
        inMemoryMusicDataSource.delete(music)
    }

    fun regenerate(music: Music) {
        delete(music)
        val generationRecord = MusicGenerationRecord(
            id = music.id,
            prompt = music.prompt,
            version = music.version + 1,
            createdAt = music.createdAt
        )
        generateMusic(generationRecord)
    }
}