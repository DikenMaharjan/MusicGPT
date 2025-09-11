package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.musicplayer.ui.theme.LocalSpacing

@Composable
fun GenerationProgressView(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Box(
        modifier = modifier.size(LocalSpacing.current.dimen64),
        contentAlignment = Alignment.Center
    ) {
        Text(text = progress.toString())
    }
}