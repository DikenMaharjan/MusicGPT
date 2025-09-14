package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.LocalThemeColor
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
