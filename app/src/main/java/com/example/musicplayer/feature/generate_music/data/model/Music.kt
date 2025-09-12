package com.example.musicplayer.feature.generate_music.data.model

import java.time.Instant

data class Music(
    val id: String,
    val title: String,
    val prompt: String,
    val image: ImageUrl,
    val song: SongUrl,
    val createdAt: Instant,
    val version: Int
)


typealias ImageUrl = String

/***
 * We are using sweepFactor in [com.example.musicplayer.feature.music_player.infrastructure.SoothingSoundPlayer]
 * to distinguish different audios
 */
typealias SongUrl = Double