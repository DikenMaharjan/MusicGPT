package com.example.musicplayer.feature.music_player.model

data class MusicControllerState(
    val playing: Boolean,
    val selectedContent: AudioContent?
)