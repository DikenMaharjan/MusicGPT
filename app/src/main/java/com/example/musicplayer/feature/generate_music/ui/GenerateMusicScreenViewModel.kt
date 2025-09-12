package com.example.musicplayer.feature.generate_music.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.repo.MusicRepository
import com.example.musicplayer.feature.generate_music.ui.api.GenerateMusicScreenStateProducer
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import com.example.musicplayer.feature.music_player.MusicController
import com.example.musicplayer.feature.music_player.model.AudioContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenerateMusicScreenViewModel @Inject constructor(
    generateMusicScreenStateProducer: GenerateMusicScreenStateProducer,
    private val musicController: MusicController,
    private val musicRepository: MusicRepository
) : ViewModel() {

    val state = generateMusicScreenStateProducer.state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = GenerateMusicScreenState()
    )


    fun generateMusic(query: String) {
        musicRepository.generateMusic(query)
    }

    fun retryGeneration(generationRecord: MusicGenerationRecord) {
        musicRepository.retryGeneration(generationRecord)
    }

    fun play(music: Music) {
        musicController.play(audioContent = AudioContent(id = music.id, sweepFactor = music.song))
    }

    fun delete(music: Music) {
        musicRepository.delete(music)
        val isDeletingMusicBeingPlayed = musicController.playingId() == music.id
        if (isDeletingMusicBeingPlayed) {
            musicController.dismiss()
        }
    }

    fun play() {
        musicController.play()
    }

    fun pause() {
        musicController.pause()
    }


    fun dismissPlayer() {
        musicController.dismiss()
    }
}