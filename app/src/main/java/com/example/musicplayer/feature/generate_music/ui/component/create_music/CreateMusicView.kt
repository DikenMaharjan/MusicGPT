package com.example.musicplayer.feature.generate_music.ui.component.create_music

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui_core.components.layout.ProvideAnimatedVisibilityScope
import com.example.musicplayer.ui_core.components.layout.sharedBoundsWithLocalScopes
import com.example.musicplayer.ui_core.haze.LocalHazeSource
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreateMusicView(
    modifier: Modifier = Modifier,
    generateMusic: (String) -> Unit
) {
    val createMusicTextFieldState = rememberCreateMusicTextFileState()
    AnimatedContent(
        modifier = modifier,
        targetState = createMusicTextFieldState.isShown
    ) { isTextFieldVisible ->
        ProvideAnimatedVisibilityScope(
            scope = this
        ) {
            if (isTextFieldVisible) {
                CreateMusicTextFieldRow(
                    modifier = Modifier
                        .imePadding()
                        .sharedBoundsForCreateViews(),
                    onFocusRemoved = createMusicTextFieldState::hide,
                    onGenerate = generateMusic
                )
            } else {
                CreateMusicButton(
                    modifier = Modifier
                        .padding(LocalSpacing.current.dimen12)
                        .clip(CircleShape)
                        .hazeEffect(
                            LocalHazeSource.current, style = HazeStyle(
                                blurRadius = 8.dp,
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                tint = null,
                            )
                        )
                        .sharedBoundsForCreateViews(),
                    onClick = createMusicTextFieldState::show
                )
            }
        }
    }
}

private class CreateMusicTextFieldState {

    var isShown by mutableStateOf(false)

    fun show() {
        isShown = true
    }

    fun hide() {
        isShown = false
    }
}

@Composable
private fun rememberCreateMusicTextFileState(): CreateMusicTextFieldState {
    return remember {
        CreateMusicTextFieldState()
    }
}


@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.sharedBoundsForCreateViews(): Modifier {
    return this.sharedBoundsWithLocalScopes(
        "musicGPT-create-bounds"
    )
}
