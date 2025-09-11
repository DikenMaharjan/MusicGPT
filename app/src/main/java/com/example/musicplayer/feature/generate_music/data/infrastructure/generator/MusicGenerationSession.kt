package com.example.musicplayer.feature.generate_music.data.infrastructure.generator

import com.example.musicplayer.feature.generate_music.data.infrastructure.data_source.InMemoryMusicDataSource
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import com.example.musicplayer.utils.coroutine.AppScope
import com.example.musicplayer.utils.coroutine.createChildScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

class MusicGenerationSession @AssistedInject constructor(
    @Assisted private val prompt: String,
    appScope: AppScope,
    private val inMemoryMusicDataSource: InMemoryMusicDataSource
) {

    private val id = UUID.randomUUID().toString()

    private val scope = appScope.createChildScope { Job(it) }

    private val _musicGenerationRecord = MutableStateFlow(
        MusicGenerationRecord(
            id = id,
            prompt = prompt
        )
    )

    val musicGenerationRecord = _musicGenerationRecord.asStateFlow()
    fun start() {
        scope.launch {
            val timeTaken = measureTime {
                processPrompt()
                generateMelody()
                generateRhythm()
                addInstruments()
                finalizeMusic()
            }
            completeGeneration(timeTaken = timeTaken)
        }
    }

    private fun completeGeneration(timeTaken: Duration) {
        // Simulate 10% chance of failure.
        if (Math.random() < 0.1) {
            setProgressAndState(
                progress = 0f,
                state = MusicGenerationState.Failed(
                    errorMessage = "Generation failed."
                )
            )
        } else {
            val generatedMusic = Music(
                title = generateTitleFromQuery(),
                prompt = prompt,
                song = Random.nextDouble(),
                image = getRandomAlbumImage(),
                createdAt = java.time.Instant.now(),
                id = id
            )
            val completedState = MusicGenerationState.Completed(
                music = generatedMusic,
                timeTaken = timeTaken
            )
            inMemoryMusicDataSource.addMusic(generatedMusic)
            setProgressAndState(
                progress = 1.0f,
                state = completedState
            )
        }
    }

    private fun processPrompt() {
        setProgressAndState(
            progress = 0.1f,
            state = MusicGenerationState.ProcessingPrompt,
        )
    }

    private suspend fun generateMelody() {
        delay(800)
        setProgressAndState(
            progress = 0.1f,
            state = MusicGenerationState.GeneratingMelody,
        )

    }

    private suspend fun generateRhythm() {
        delay(1000)
        setProgressAndState(
            progress = 0.5f,
            state = MusicGenerationState.GeneratingRhythm
        )
    }

    private suspend fun addInstruments() {
        for (instrumentCount in 1..3) {
            delay(600)
            setProgressAndState(
                progress = 0.5f + (instrumentCount * 0.1f),
                state = MusicGenerationState.AddingInstruments(
                    instrumentCount = instrumentCount,
                    totalInstruments = 3
                )
            )
        }
    }

    private suspend fun finalizeMusic() {
        delay(400)
        setProgressAndState(
            progress = 0.95f,
            state = MusicGenerationState.Finalizing
        )
    }

    private fun setProgressAndState(progress: Float, state: MusicGenerationState) {
        _musicGenerationRecord.update {
            it.copy(
                progress = progress,
                state = state
            )
        }
    }

    private fun generateTitleFromQuery(): String {
        val prefix = prompt.split(" ")
            .joinToString("") { it.first().uppercase() }
        return "($prefix) Music"
    }

    companion object {
        fun getRandomAlbumImage() = "https://picsum.photos/id/${Random.nextInt(1000)}"
    }
}

@AssistedFactory
interface MusicGenerationSessionFactory {
    fun create(
        prompt: String
    ): MusicGenerationSession
}