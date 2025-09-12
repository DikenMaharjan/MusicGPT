package com.example.musicplayer.feature.generate_music.ui.component.floating_player

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.positionInRoot


@OptIn(ExperimentalLayoutApi::class)
class CustomBottomInsets(
    private val mutableInset: MutableWindowInsets = MutableWindowInsets()
) : WindowInsets by mutableInset {

    fun setLayoutPosition(layoutCoordinates: LayoutCoordinates) {
        val rootCoordinates = layoutCoordinates.findRootCoordinates()
        val rootHeight = rootCoordinates.size.height
        val yPos = layoutCoordinates.positionInRoot().y
        mutableInset.insets = WindowInsets(bottom = rootHeight - yPos.toInt())
    }

    companion object {
        val BottomInsets
            @Composable
            get() = LocalCustomBottomInsets.current
    }
}

val LocalCustomBottomInsets =
    compositionLocalOf<CustomBottomInsets> { error("No CustomBottomInsets provider") }

@Composable
fun Modifier.customBottomInsets(
    bottomInsets: CustomBottomInsets
): Modifier {
    return this
        .windowInsetsPadding(bottomInsets)

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun rememberCustomBottomInsets(): CustomBottomInsets {
    return remember {
        CustomBottomInsets()
    }
}

@Composable
fun ProvideCustomBottomInsets(
    customBottomInsets: CustomBottomInsets,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomBottomInsets provides customBottomInsets,
        content = content
    )
}