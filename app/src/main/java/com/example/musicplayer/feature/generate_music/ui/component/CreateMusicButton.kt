package com.example.musicplayer.feature.generate_music.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme

@Composable
fun CreateMusicButton(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(LocalThemeColor.current.white.copy(alpha = 0.1f))
            .padding(
                horizontal = LocalSpacing.current.dimen18,
                vertical = LocalSpacing.current.dimen12
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
    ) {
        Image(
            painter = painterResource(R.drawable.stars),
            contentDescription = null
        )
        Text("Create")
    }
}

@Preview
@Composable
private fun CreateMusicButtonPrev() {
    PreviewTheme {
        CreateMusicButton()
    }
}