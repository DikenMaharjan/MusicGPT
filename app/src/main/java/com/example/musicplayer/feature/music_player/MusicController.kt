package com.example.musicplayer.feature.music_player

import com.example.musicplayer.feature.music_player.infrastructure.SoothingSoundPlayer
import com.example.musicplayer.feature.music_player.model.AudioContent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicController @Inject constructor(
    private val soothingSoundPlayer: SoothingSoundPlayer
) {
    private val _selectedContent = MutableStateFlow<AudioContent?>(null)
    val selectedContent = _selectedContent.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    fun toggle() {
        if (_isPlaying.value) {
            pause()
        } else {
            play()
        }
    }

    private fun play() {
        val selectedContent = _selectedContent.value
        if (selectedContent != null) {
            soothingSoundPlayer.play(selectedContent.sweepFactor)
            _isPlaying.update { true }
        }
    }

    fun play(audioContent: AudioContent) {
        _selectedContent.update { audioContent }
        play()
    }


    private fun pause() {
        soothingSoundPlayer.stop()
        _isPlaying.update { false }
    }

}