package com.example.musicplayer.feature.generate_music.ui.component.floating_player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.ui.component.MusicAlbumView
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme
import com.example.musicplayer.ui_core.preview_parameter.BooleanPreviewParameter
import java.time.Instant

@Composable
fun FloatingPlayerView(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    music: Music,
    musicControlActions: MusicControlActions
) {
    val shape = MaterialTheme.shapes.large

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.dimen8)
            .background(Color.Transparent)
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.8f),
                    radius = LocalSpacing.current.dimen4,
                )
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .background(LocalThemeColor.current.primary.p200.copy(alpha = 0.4f))
            .border(
                width = LocalSpacing.current.dimen1,
                color = LocalThemeColor.current.white.copy(alpha = 0.05f),
                shape = shape
            )
            .padding(LocalSpacing.current.dimen8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen8)
    ) {
        MusicAlbumView(
            modifier = Modifier.size(LocalSpacing.current.dimen54),
            music = music
        )
        Text(
            modifier = Modifier.weight(1f),
            text = music.title
        )
        MusicControlsRow(
            isPlaying = isPlaying,
            musicControlActions = musicControlActions
        )
    }
}


@Composable
private fun MusicControlsRow(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    musicControlActions: MusicControlActions
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
    ) {
        IconButton(
            onClick = musicControlActions.playPrevious
        ) {
            Icon(
                imageVector = Icons.Default.SkipPrevious,
                contentDescription = "Play Previous"
            )
        }
        if (isPlaying) {
            IconButton(
                onClick = musicControlActions.pause
            ) {
                Icon(
                    imageVector = Icons.Default.Pause,
                    contentDescription = "Pause"
                )
            }
        } else {
            IconButton(
                onClick = musicControlActions.play
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play"
                )
            }
        }
    }
    IconButton(
        onClick = musicControlActions.playNext
    ) {
        Icon(
            imageVector = Icons.Default.SkipNext,
            contentDescription = "Play Next"
        )
    }
}

data class MusicControlActions(
    val playPrevious: () -> Unit,
    val playNext: () -> Unit,
    val pause: () -> Unit,
    val play: () -> Unit,
    val dismiss: () -> Unit
)

@Preview
@Composable
fun FloatingPlayerViewPreview(
    @PreviewParameter(BooleanPreviewParameter::class) isPlaying: Boolean
) {
    PreviewTheme {
        val music = remember {
            Music(
                id = "id",
                title = "Preview Music",
                prompt = "A prompt for the music",
                image = "image_url",
                song = 0.5,
                createdAt = Instant.now()
            )
        }
        val musicControlActions = remember { MusicControlActions({}, {}, {}, {}, {}) }

        FloatingPlayerView(
            isPlaying = isPlaying,
            music = music,
            musicControlActions = musicControlActions
        )
    }
}