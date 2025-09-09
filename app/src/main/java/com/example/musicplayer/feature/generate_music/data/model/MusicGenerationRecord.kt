package com.example.musicplayer.feature.generate_music.data.model

import java.time.Instant

data class MusicGenerationRecord(
    val id: String,
    val prompt: String,
    val progress: Float = 0.0f,
    val version: Int = 1,
    val createdAt: Instant = Instant.now(),
    val state: MusicGenerationState = MusicGenerationState.Initializing
)


sealed class MusicGenerationState {
    data object Initializing : MusicGenerationState()
    data object ProcessingPrompt : MusicGenerationState()
    data object GeneratingMelody : MusicGenerationState()
    data object GeneratingRhythm : MusicGenerationState()
    data class AddingInstruments(
        val instrumentCount: Int = 0,
        val totalInstruments: Int = 0
    ) : MusicGenerationState()

    data object Finalizing : MusicGenerationState()
    data class Completed(
        val music: Music,
        val timeTaken: kotlin.time.Duration
    ) : MusicGenerationState()

    data class Failed(val errorMessage: String) : MusicGenerationState()
    data class Cancelled(
        val cancelledAt: Instant = Instant.now()
    ) : MusicGenerationState()
}
