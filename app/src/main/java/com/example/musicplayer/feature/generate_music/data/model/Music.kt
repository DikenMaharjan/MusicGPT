package com.example.musicplayer.feature.generate_music.data.model

import androidx.compose.ui.graphics.painter.Painter
import java.time.Instant

data class Music(
    val id: String,
    val title: String,
    val prompt: String,
    val image: Painter,
    val createdAt: Instant
)


data class GeneratingMusic(
    val prompt: String,
    val progress: Float,
    val version: Int,
    val createdAt: Instant
)
