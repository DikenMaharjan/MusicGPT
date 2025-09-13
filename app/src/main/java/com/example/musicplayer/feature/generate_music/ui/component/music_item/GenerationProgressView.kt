package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme
import kotlin.math.cos
import kotlin.math.sin

fun Modifier.progressBorder(): Modifier = this.composed {
    val infiniteTransition = rememberInfiniteTransition()
    val themeColors = LocalThemeColor.current

    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val gradientColors = listOf(
        themeColors.logoPurple,
        Color.Transparent,
        themeColors.logoPink,
        themeColors.logoYellow,
        Color.Transparent,
        themeColors.logoOrange
    )

    val normalizedPhase = phase % 4f
    val phaseIndex = normalizedPhase.toInt()
    val transitionProgress = normalizedPhase - phaseIndex

    val progress = when {
        transitionProgress < 0.8f -> transitionProgress / 0.8f
        else -> 1f - ((transitionProgress - 0.8f) / 0.2f)
    }

    val alpha = when {
        transitionProgress < 0.8f -> 1f
        else -> 1f - ((transitionProgress - 0.8f) / 0.2f) * 0.3f
    }
    val currentBrush = when (phaseIndex) {
        0 -> Brush.linearGradient(
            colors = gradientColors.map { it.copy(alpha = it.alpha * alpha) },
            start = Offset(progress * 200f - 100f, 0f),
            end = Offset(progress * 200f + 100f, 0f),
            tileMode = TileMode.Mirror
        )

        1 -> Brush.linearGradient(
            colors = gradientColors.map { it.copy(alpha = it.alpha * alpha) },
            start = Offset(0f, progress * 200f - 100f),
            end = Offset(0f, progress * 200f + 100f)
        )

        2 -> Brush.radialGradient(
            colors = gradientColors,
            radius = (progress * 0.5f + 0.3f) * 200f
        )

        else -> {
            val angle = progress * 360f
            val offsetX = cos(Math.toRadians(angle.toDouble())).toFloat() * 100f
            val offsetY = sin(Math.toRadians(angle.toDouble())).toFloat() * 100f
            Brush.linearGradient(
                colors = gradientColors.map { it.copy(alpha = it.alpha * alpha) },
                start = Offset(-offsetX, -offsetY),
                end = Offset(offsetX, offsetY)
            )
        }
    }
    border(
        width = 0.5.dp,
        brush = currentBrush,
        shape = MaterialTheme.shapes.large
    )
}

@Composable
fun GenerationProgressView(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Box(
        modifier = modifier
            .size(LocalSpacing.current.dimen64)
            .progressBorder()
            .clip(MaterialTheme.shapes.large),
        contentAlignment = Alignment.Center
    ) {
        CrossfadeImageSequence(
            images = listOf(
                R.drawable.property_1_0 forProgress 0f,
                R.drawable.property_1_25 forProgress 0.25f,
                R.drawable.property_1_50 forProgress 0.5f,
                R.drawable.property_1_75 forProgress 0.75f,
                R.drawable.property_1_90 forProgress 0.9f,
                R.drawable.property_1_100 forProgress 1f
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

infix fun Int.forProgress(progress: Float) = ImageWithProgress(this, progress)

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
            appearingImage.image to localProgress
        ).forEach { (image, alpha) ->
            Image(
                painter = painterResource(image),
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