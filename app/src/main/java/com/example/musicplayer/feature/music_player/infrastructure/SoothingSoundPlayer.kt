package com.example.musicplayer.feature.music_player.infrastructure

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.math.sin

class SoothingSoundPlayer @Inject constructor() {

    private val currentSweepFactor = AtomicReference<Double?>(null)

    private val audioTrack = createAudioTrack()
    private var playerThread: Thread? = null

    fun play(sweepFactor: Double) {
        if (currentSweepFactor.get() == sweepFactor) return
        currentSweepFactor.set(sweepFactor)
        playerThread = thread(start = true) {
            playLoop(sweepFactor)
        }
    }

    fun stop() {
        currentSweepFactor.set(null)
        playerThread?.join()
        playerThread = null
    }

    private fun playLoop(sweepFactor: Double) {
        try {
            audioTrack.play()
            var sampleIndex = 0
            while (sweepFactor == currentSweepFactor.get()) {
                val samples =
                    generateChunk(sweepFactor, sampleIndex)
                audioTrack.write(samples, 0, samples.size)
                sampleIndex = (sampleIndex + CHUNK_SIZE) % numSamples
            }
        } finally {
            audioTrack.pause()
            audioTrack.flush()
            currentSweepFactor.set(null)
        }
    }

    private fun generateChunk(
        sweepFactor: Double,
        startIndex: Int,
    ): ShortArray {
        val samples = ShortArray(CHUNK_SIZE)
        val minFrequency = 220.0
        val maxFrequency = 880.0
        val midFrequency = (minFrequency + maxFrequency) / 2
        val amplitude = (maxFrequency - minFrequency) / 2

        for (i in 0 until CHUNK_SIZE) {
            val t = (startIndex + i).toDouble() / SAMPLE_RATE
            val frequency =
                midFrequency + amplitude * sin(2 * Math.PI * t * sweepFactor / (DURATION_MS / 1000.0))
            val angle = 2 * Math.PI * frequency * t
            samples[i] = (sin(angle) * Short.MAX_VALUE).toInt().toShort()
        }

        return samples
    }

    private fun createAudioTrack(): AudioTrack {
        val bufferSize = AudioTrack.getMinBufferSize(
            SAMPLE_RATE,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        return AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(SAMPLE_RATE)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()
    }

    companion object {
        private const val SAMPLE_RATE = 44100
        private const val CHUNK_SIZE = 1024
        private const val DURATION_MS = 2000

        private val numSamples = (DURATION_MS / 1000.0 * SAMPLE_RATE).toInt()

    }
}
