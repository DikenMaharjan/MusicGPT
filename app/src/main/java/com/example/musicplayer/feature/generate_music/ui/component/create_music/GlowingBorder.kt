package com.example.musicplayer.feature.generate_music.ui.component.create_music

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

fun Modifier.glowingBorder(
    colorCount: Int = 4
): Modifier = this
    .composed {
        val infiniteTransition = rememberInfiniteTransition()

        val phase by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = colorCount * 3000),
                repeatMode = RepeatMode.Restart
            )
        )
        val alpha by infiniteTransition.animateFloat(
            0.65f, 0.8f,
            infiniteRepeatable(
                tween(2000, easing = LinearEasing),
                RepeatMode.Reverse
            )
        )
        val borderAlpha by infiniteTransition.animateFloat(
            0.65f, 0.9f,
            infiniteRepeatable(
                tween(3000),
                RepeatMode.Reverse
            )
        )
        val radius by infiniteTransition.animateFloat(
            65f, 100f,
            animationSpec = infiniteRepeatable(
                tween(5000),
                RepeatMode.Reverse
            )
        )

        val globalIndexFloat = phase * colors.size
        val baseIndex = globalIndexFloat.toInt()
        val localFraction = globalIndexFloat - baseIndex

        val windowColorsList = remember(colorCount) {
            MutableList(colorCount) { Color.Transparent }
        }

        repeat(colorCount) { offset ->
            val firstIndex = (baseIndex + offset) % colors.size
            val secondIndex = (firstIndex + 1) % colors.size
            windowColorsList[offset] = lerp(colors[firstIndex], colors[secondIndex], localFraction)
        }


        val density = LocalDensity.current

        dropShadow(
            shape = CircleShape,
            block = {
                this.color = Color.White
                this.radius = with(density) {
                    8.dp.toPx()
                }
                this.alpha = borderAlpha
            }
        ).dropShadow(
            shape = RectangleShape,
            block = {
                this.radius = radius
                this.brush = Brush.linearGradient(
                    colors = windowColorsList,
                    tileMode = TileMode.Mirror
                )
                this.alpha = alpha
            }
        )
    }


private val colors: List<Color> = listOf(
    Color(0xFF64006E),
    Color(0xFF645300),
    Color(0xFF00739A),
    Color(0xFF018560),
    Color(0xFF910000),
    Color(0xFFA13D00),
    Color(0xFF8A0047),
)