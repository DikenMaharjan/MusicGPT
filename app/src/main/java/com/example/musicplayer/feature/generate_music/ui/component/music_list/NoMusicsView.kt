package com.example.musicplayer.feature.generate_music.ui.component.music_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.PreviewTheme

@Composable
fun NoMusicView(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(LocalSpacing.current.dimen28)
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Icon(
            imageVector = Icons.Default.MusicOff,
            contentDescription = null,
            modifier = Modifier
                .size(LocalSpacing.current.dimen120)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.dimen8))
        Text(
            text = stringResource(R.string.no_music_view_empty_library_title),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(LocalSpacing.current.dimen16),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.no_music_view_empty_library_message),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(LocalSpacing.current.dimen16),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}


@Preview
@Composable
private fun NoMusicsViewPreview() {
    PreviewTheme {
        NoMusicView()
    }
}