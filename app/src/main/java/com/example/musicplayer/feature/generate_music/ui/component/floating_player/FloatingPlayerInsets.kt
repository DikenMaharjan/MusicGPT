package com.example.musicplayer.feature.generate_music.ui.component.floating_player

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.positionInRoot


@OptIn(ExperimentalLayoutApi::class)
class FloatingPlayerInsets(
    private val mutableInset: MutableWindowInsets = MutableWindowInsets()
) : WindowInsets by mutableInset {

    fun setPlayerPosition(layoutCoordinates: LayoutCoordinates) {
        val rootCoordinates = layoutCoordinates.findRootCoordinates()
        val rootHeight = rootCoordinates.size.height
        val yPos = layoutCoordinates.positionInRoot().y
        mutableInset.insets = WindowInsets(bottom = rootHeight - yPos.toInt())
    }
}

val LocalFloatingPlayerInsets =
    staticCompositionLocalOf<FloatingPlayerInsets> { error("No FloatingPlayerInsets provider") }

@Composable
fun Modifier.floatingPlayerInsets(): Modifier {
    val floatingPlayerInsets = LocalFloatingPlayerInsets.current
    return this
        .windowInsetsPadding(floatingPlayerInsets)
        .consumeWindowInsets(floatingPlayerInsets)

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun rememberFloatingPlayerInset(): FloatingPlayerInsets {
    return remember {
        FloatingPlayerInsets()
    }
}

@Composable
fun ProvideFloatingPlayerInsets(
    bottomPlayerInsets: FloatingPlayerInsets = rememberFloatingPlayerInset(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalFloatingPlayerInsets provides bottomPlayerInsets,
        content = content
    )
}