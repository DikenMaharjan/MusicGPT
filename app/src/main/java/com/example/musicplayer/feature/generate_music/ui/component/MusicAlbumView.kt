package com.example.musicplayer.feature.generate_music.ui.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import coil3.compose.AsyncImage
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.ui.theme.LocalThemeColor

@Composable
 fun MusicAlbumView(
    modifier: Modifier = Modifier,
    music: Music
) {
    AsyncImage(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(LocalThemeColor.current.primary.p1000),
        model = music.image,
        contentDescription = null,
        fallback = rememberVectorPainter(Icons.Outlined.MusicNote),
        placeholder = rememberVectorPainter(Icons.Outlined.MusicNote),
        error = rememberVectorPainter(Icons.Outlined.MusicNote),
    )
}