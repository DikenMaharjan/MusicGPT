package com.example.musicplayer.ui_core.modifier.swipe_to_dismiss

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Modifier.swipeToDismiss(
    orientation: Orientation = Orientation.Horizontal,
    onDismiss: () -> Unit
): Modifier {
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = SwipeToDismissAnchors.Start
        )
    }
    LaunchedEffect(Unit) {
        snapshotFlow { anchoredDraggableState.targetValue }
            .collectLatest {
                if (it == SwipeToDismissAnchors.End) {
                    onDismiss()
                }
            }
    }
    return this
        .onSizeChanged { size ->
            val endValue = when (orientation) {
                Orientation.Vertical -> size.height
                Orientation.Horizontal -> size.width
            }
            anchoredDraggableState.updateAnchors(
                newAnchors = DraggableAnchors {
                    SwipeToDismissAnchors.Start at 0f
                    SwipeToDismissAnchors.End at endValue.toFloat()
                }
            )
        }
        .anchoredDraggable(state = anchoredDraggableState, orientation = orientation)
        .offset {
            val offset = anchoredDraggableState.offset
            when (orientation) {
                Orientation.Vertical -> IntOffset(0, offset.toInt())
                Orientation.Horizontal -> IntOffset(offset.toInt(), 0)
            }
        }

}

enum class SwipeToDismissAnchors {
    Start, End
}