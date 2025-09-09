package com.example.musicplayer.feature.generate_music.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.feature.generate_music.data.model.Music
import com.example.musicplayer.ui.theme.LocalSpacing

@Composable
fun MusicItem(
    modifier: Modifier = Modifier,
    music: Music
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = music.image,
            contentDescription = null
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
        ) {
            Text(text = music.title, style = MaterialTheme.typography.bodyLarge)
            Text(text = music.prompt, style = MaterialTheme.typography.bodySmall)
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