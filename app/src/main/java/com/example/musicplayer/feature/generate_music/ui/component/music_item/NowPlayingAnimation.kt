package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.PreviewTheme

@Composable
fun NowPlayingAnimation(
    modifier: Modifier = Modifier,
) {
    val barCount = 3
    val maxHeight = LocalSpacing.current.dimen16
    val minHeight = LocalSpacing.current.dimen8

    Row(
        modifier = modifier.size(maxHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(barCount) { index ->
            val anim = rememberInfiniteTransition().animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 500,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 150)
                )
            )
            val height by remember { derivedStateOf { minHeight + (maxHeight - minHeight) * anim.value } }

            Surface(
                modifier = Modifier
                    .width(remember(maxHeight) { maxHeight / 5 })
                    .height(height)
                    .clip(RoundedCornerShape(LocalSpacing.current.dimen2)),
                color = LocalContentColor.current,
                content = {}
            )
        }
    }
}

@Preview
@Composable
private fun PlayingIconPrev() {
    PreviewTheme {
        NowPlayingAnimation()
    }
}
