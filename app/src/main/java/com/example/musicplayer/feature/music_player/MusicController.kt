package com.example.musicplayer.feature.music_player

import com.example.musicplayer.feature.music_player.infrastructure.SoothingSoundPlayer
import com.example.musicplayer.feature.music_player.model.AudioContent
import com.example.musicplayer.feature.music_player.model.MusicControllerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicController @Inject constructor(
    private val soothingSoundPlayer: SoothingSoundPlayer
) {
    private val selectedContent = MutableStateFlow<AudioContent?>(null)
    private val isPlaying = MutableStateFlow(false)

    val musicPlayerState = combine(selectedContent, isPlaying) { content, isPlaying ->
        MusicControllerState(
            playing = isPlaying,
            selectedContent = content
        )
    }

    fun play() {
        val selectedContent = selectedContent.value
        if (selectedContent != null) {
            soothingSoundPlayer.play(selectedContent.sweepFactor)
            isPlaying.update { true }
        }
    }

    fun play(audioContent: AudioContent) {
        selectedContent.update { audioContent }
        play()
    }


    fun pause() {
        soothingSoundPlayer.stop()
        isPlaying.update { false }
    }

    fun dismiss() {
        pause()
        selectedContent.update { null }
    }

    fun playingId(): String? {
        return selectedContent.value?.id
    }
}