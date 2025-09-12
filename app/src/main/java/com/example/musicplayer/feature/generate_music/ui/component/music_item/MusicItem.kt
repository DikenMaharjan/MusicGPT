package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.feature.generate_music.ui.component.MusicAlbumView
import com.example.musicplayer.feature.generate_music.ui.model.GenerateMusicListItem
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme
import java.time.Instant

@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    musicItem: GenerateMusicListItem.MusicItem
) {
    val music = musicItem.music
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.dimen8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen12)
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            MusicAlbumView(
                modifier = Modifier
                    .size(LocalSpacing.current.dimen64),
                music = music
            )
            if (musicItem.isPlaying) {
                NowPlayingAnimation(
                    modifier = Modifier
                        .matchParentSize()
                        .background(LocalThemeColor.current.primary.p1100.copy(alpha = 0.4f))
                        .wrapContentSize()

                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
        ) {
            Text(text = music.title, style = MaterialTheme.typography.bodyLarge, maxLines = 1)
            Text(
                text = music.prompt,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                color = LocalThemeColor.current.primary.p1100,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(R.string.music_item_more_icon_description)
            )
        }
    }
}

@Preview
@Composable
private fun MusicItemPreview() {
    PreviewTheme {
        val music = Music(
            id = "1",
            title = "In the style of Vivaldi",
            prompt = "A cheerful and uplifting orchestral piece in the style of Vivaldi, featuring a solo violin.",
            image = "https://cdn.openai.com/audio/sunos/v0/2023-10-25-17-02-53/images/image.png",
            song = 0.234,
            createdAt = Instant.parse("2023-10-25T17:02:53Z")
        )
        MusicItem(
            musicItem = GenerateMusicListItem.MusicItem(
                music = music,
                isPlaying = true
            )
        )
    }
}