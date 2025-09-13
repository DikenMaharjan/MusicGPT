package com.example.musicplayer.feature.generate_music.data.infrastructure.generator

import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.measureTime

class MusicGenerator @Inject constructor() {
    fun generate(prompt: String): Flow<MusicGenerationUpdate> {
        return flow {
            val timeTaken = measureTime {
                initialize()
                processPrompt()
                generateMelody()
                generateRhythm()
                addInstruments()
                finalizeMusic()
            }
            delay(5.seconds)
            completeGeneration(
                timeTaken = timeTaken, prompt = prompt
            )
        }
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.initialize() {
        emitUpdate(0f, MusicGenerationState.Initializing)
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.completeGeneration(
        prompt: String,
        timeTaken: Duration
    ) {
        // Simulate 10% chance of failure.
        if (Math.random() < 0.1) {
            error("Simulated failure.")
        } else {
            val completedState = MusicGenerationState.Completed(
                title = generateTitleFromQuery(prompt),
                prompt = prompt,
                song = Random.nextDouble(),
                image = getRandomAlbumImage(),
                createdAt = java.time.Instant.now(),
                timeTaken = timeTaken
            )
            emitUpdate(
                progress = 1.0f,
                state = completedState
            )
        }
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.processPrompt() {
        randomDelay()
        emitUpdate(
            progress = 0.1f,
            state = MusicGenerationState.ProcessingPrompt,
        )
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.generateMelody() {
        randomDelay()
        emitUpdate(
            progress = 0.1f,
            state = MusicGenerationState.GeneratingMelody,
        )

    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.generateRhythm() {
        randomDelay()
        emitUpdate(
            progress = 0.5f,
            state = MusicGenerationState.GeneratingRhythm
        )
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.addInstruments() {
        for (instrumentCount in 1..3) {
            randomDelay()
            emitUpdate(
                progress = 0.5f + (instrumentCount * 0.1f),
                state = MusicGenerationState.AddingInstruments(
                    instrumentCount = instrumentCount,
                    totalInstruments = 3
                )
            )
        }
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.finalizeMusic() {
        randomDelay()
        emitUpdate(
            progress = 0.95f,
            state = MusicGenerationState.Finalizing
        )
    }

    private suspend fun FlowCollector<MusicGenerationUpdate>.emitUpdate(
        progress: Float,
        state: MusicGenerationState
    ) {
        emit(MusicGenerationUpdate(progress = progress, state = state))
    }

    private fun generateTitleFromQuery(prompt: String): String {
        val prefix = prompt.split(" ")
            .filter { it.isNotBlank() }
            .joinToString("") { it.first().uppercase() }
        return "($prefix) Music"
    }


    suspend fun randomDelay() {
        delay(Random.nextLong(500, 2000))
    }

    companion object Companion {
        fun getRandomAlbumImage() = "https://picsum.photos/id/${Random.nextInt(500)}/300"
    }
}

data class MusicGenerationUpdate(
    val progress: Float,
    val state: MusicGenerationState
)