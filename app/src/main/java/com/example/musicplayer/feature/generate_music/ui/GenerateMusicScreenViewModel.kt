package com.example.musicplayer.feature.generate_music.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.feature.generate_music.ui.api.GenerateMusicScreenStateProducer
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenerateMusicScreenViewModel @Inject constructor(
    generateMusicScreenStateProducer: GenerateMusicScreenStateProducer,
) : ViewModel() {

    val state = generateMusicScreenStateProducer.state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        initialValue = GenerateMusicScreenState()
    )

}