package com.example.musicplayer.feature.generate_music.ui.component.music_item

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationRecord
import com.example.musicplayer.feature.generate_music.data.model.MusicGenerationState
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme

@Composable
fun MusicGenerationItem(
    modifier: Modifier = Modifier,
    generationRecord: MusicGenerationRecord,
    retryGeneration: (MusicGenerationRecord) -> Unit
) {
    val animatedProgress by animateFloatAsState(
        targetValue = generationRecord.progress,
        animationSpec = if (generationRecord.progress == 0f) snap() else tween(4000)
    )

    BackgroundProgressLayout(
        modifier = modifier.fillMaxWidth(),
        progress = animatedProgress
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.dimen8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen12)
        ) {
            GenerationProgressView(
                progress = animatedProgress
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen4)
            ) {
                Text(text = generationRecord.prompt, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = generationRecord.state.msg,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalThemeColor.current.primary.p1100
                )
            }
            if (generationRecord.state is MusicGenerationState.Failed) {
                IconButton(
                    onClick = { retryGeneration(generationRecord) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        tint = Color.Red,
                        contentDescription = "Retry"
                    )
                }
            } else {
                Text(
                    modifier = Modifier
                        .border(
                            width = LocalSpacing.current.dimen2,
                            color = LocalThemeColor.current.primary.p800,
                            shape = MaterialTheme.shapes.extraLarge

                        )
                        .padding(
                            horizontal = LocalSpacing.current.dimen18,
                            vertical = LocalSpacing.current.dimen8
                        ),
                    text = "v${generationRecord.version}",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


private val MusicGenerationState.msg: String
    @Composable
    get() = when (this) {
        is MusicGenerationState.AddingInstruments -> stringResource(id = R.string.music_generation_state_adding_instruments)
        is MusicGenerationState.Cancelled -> stringResource(id = R.string.music_generation_state_cancelled)
        is MusicGenerationState.Completed -> stringResource(id = R.string.music_generation_state_completed)
        is MusicGenerationState.Failed -> stringResource(id = R.string.music_generation_state_failed)
        MusicGenerationState.Finalizing -> stringResource(id = R.string.music_generation_state_finalizing)
        MusicGenerationState.GeneratingMelody -> stringResource(id = R.string.music_generation_state_generating_melody)
        MusicGenerationState.GeneratingRhythm -> stringResource(id = R.string.music_generation_state_generating_rhythm)
        MusicGenerationState.Initializing -> stringResource(id = R.string.music_generation_state_initializing)
        MusicGenerationState.ProcessingPrompt -> stringResource(id = R.string.music_generation_state_processing_prompt)
    }

@Composable
private fun BackgroundProgressLayout(
    modifier: Modifier = Modifier,
    progress: Float,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    0f to LocalThemeColor.current.primary.p100,
                    (progress - 0.05f).coerceAtLeast(0f) to LocalThemeColor.current.primary.p100,
                    (progress + 0.05f).coerceAtMost(1f) to Color.Transparent,
                    1f to Color.Transparent
                )
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        content()
    }
}

@Preview
@Composable
private fun BackgroundProgressBarPreview() {
    PreviewTheme {
        BackgroundProgressLayout(
            modifier = Modifier.size(100.dp, 50.dp),
            progress = 0.5f,
            content = {
                Text(text = "Preview Item")
            }
        )
    }
}

@Preview
@Composable
fun MusicGenerationItemPreview() {
    PreviewTheme {
        MusicGenerationItem(
            generationRecord = MusicGenerationRecord(
                id = "123",
                prompt = "A funky beat",
                progress = 0.5f,
                state = MusicGenerationState.ProcessingPrompt
            ),
            retryGeneration = {} // Added dummy lambda for preview
        )
    }
}

@Preview
@Composable
fun MusicGenerationItemFailedPreview() { // Added a preview for the failed state
    PreviewTheme {
        MusicGenerationItem(
            generationRecord = MusicGenerationRecord(
                id = "124",
                prompt = "A sad piano melody",
                progress = 0.2f,
                state = MusicGenerationState.Failed("Something went wrong")
            ),
            retryGeneration = {}
        )
    }
}

