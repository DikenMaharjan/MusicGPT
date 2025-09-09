package com.example.musicplayer.feature.generate_music.data.model

import java.time.Instant

data class Music(
    val id: String,
    val title: String,
    val prompt: String,
    val image: ImageUrl,
    val song: SongUrl,
    val createdAt: Instant
)


typealias ImageUrl = String
typealias SongUrl = String