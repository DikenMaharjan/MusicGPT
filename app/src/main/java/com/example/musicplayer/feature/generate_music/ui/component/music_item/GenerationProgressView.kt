package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.PreviewTheme

@Composable
fun GenerationProgressView(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Box(
        modifier = modifier.size(LocalSpacing.current.dimen64),
        contentAlignment = Alignment.Center
    ) {
        CrossfadeImageSequence(
            images = listOf(
                R.drawable.property_1_0 with 0f,
                R.drawable.property_1_25 with 0.25f,
                R.drawable.property_1_50 with 0.5f,
                R.drawable.property_1_75 with 0.75f,
                R.drawable.property_1_90 with 0.9f,
                R.drawable.property_1_100 with 1f
            ),
            progress = progress
        )
        Text(
            text = "${(progress * 100).toInt()}%"
        )
    }
}


data class ImageWithProgress(
    @param:DrawableRes val image: Int,
    val progress: Float
)

infix fun Int.with(progress: Float) = ImageWithProgress(this, progress)

@Composable
private fun CrossfadeImageSequence(
    modifier: Modifier = Modifier,
    images: List<ImageWithProgress>,
    progress: Float,
) {
    val sortedImages = remember(images) {
        images.sortedBy { it.progress }
    }

    val index = sortedImages.indexOfFirst { it.progress >= progress }
    val (disappearingImage, appearingImage) =
        when {
            index <= 0 -> sortedImages[0] to (sortedImages.getOrNull(1) ?: sortedImages[0])
            index in 1 until sortedImages.size -> sortedImages[index - 1] to sortedImages[index]
            else -> sortedImages[sortedImages.lastIndex] to sortedImages[sortedImages.lastIndex]
        }

    val maxValue = (appearingImage.progress - disappearingImage.progress)
    val traversed = (progress - disappearingImage.progress)
    val localProgress = if (maxValue == 0f) {
        progress
    } else {
        traversed / maxValue
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        listOf(
            (disappearingImage.image to (1 - localProgress)),
            appearingImage to localProgress
        ).forEach { (image, alpha) ->
            Image(
                painter = painterResource(disappearingImage.image),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        this.alpha = alpha
                    },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview
@Composable
private fun GenerationProgressViewPreview() {
    PreviewTheme {
        var target by remember {
            mutableFloatStateOf(0f)
        }
        val progress by animateFloatAsState(
            target,
            animationSpec = tween(10000)
        )
        LaunchedEffect(Unit) {
            target = 1f
        }
        GenerationProgressView(
            progress = progress
        )
    }


}