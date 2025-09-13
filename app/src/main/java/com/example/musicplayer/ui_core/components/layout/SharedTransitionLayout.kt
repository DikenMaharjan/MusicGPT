package com.example.musicplayer.ui_core.components.layout

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView


@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
    compositionLocalOf { error("LocalSharedTransitionScope not provided") }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalAnimatedVisibilityScope: ProvidableCompositionLocal<AnimatedVisibilityScope> =
    compositionLocalOf { error("LocalAnimatedVisibilityScope not provided") }


@Composable
fun ProvideAnimatedVisibilityScope(
    scope: AnimatedVisibilityScope,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAnimatedVisibilityScope provides scope) {
        content()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreateSharedTransitionScope(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    SharedTransitionLayout(
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            content()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.withLocalSharedTransitionScope(block: @Composable SharedTransitionScope.(Modifier) -> Modifier): Modifier {
    return if (!LocalView.current.isInEditMode) {
        val modifier = this
        with(LocalSharedTransitionScope.current) {
            block(modifier)
        }
    } else {
        this
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedBoundsWithLocalScopes(key: Any): Modifier {
    return withLocalSharedTransitionScope {
        it.sharedBounds(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.sharedElementWithLocalScopes(key: Any): Modifier {
    return withLocalSharedTransitionScope {
        it.sharedElement(
            sharedContentState = rememberSharedContentState(key),
            animatedVisibilityScope = LocalAnimatedVisibilityScope.current,
        )
    }
}
