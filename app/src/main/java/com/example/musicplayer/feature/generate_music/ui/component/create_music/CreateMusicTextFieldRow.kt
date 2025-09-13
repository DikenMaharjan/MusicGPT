package com.example.musicplayer.feature.generate_music.ui.component.create_music

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor
import com.example.musicplayer.ui.theme.PreviewTheme
import com.example.musicplayer.ui_core.components.tap_to_remove_focus.clearFocusOnTap
import com.example.musicplayer.ui_core.modifier.rippleLessClickable
import kotlinx.coroutines.flow.dropWhile

@Composable
fun CreateMusicTextFieldRow(
    modifier: Modifier = Modifier,
    onFocusRemoved: () -> Unit,
    onGenerate: (String) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val onRemoved by rememberUpdatedState(onFocusRemoved)

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BackHandler {
        onRemoved()
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isFocused by interactionSource.collectIsFocusedAsState()
    LaunchedEffect(Unit) {
        snapshotFlow { isFocused }
            // Drop the state when the text field is not focused during composition
            .dropWhile { isFocused -> !isFocused }
            .collect { isFocused ->
                if (!isFocused) {
                    onRemoved()
                }
            }
    }
    val focusManager = LocalFocusManager.current
    val textFieldState = rememberTextFieldState()

    val onDone = {
        if (textFieldState.text.isNotBlank()) {
            focusManager.clearFocus(force = true)
            onGenerate(textFieldState.text.toString())
            textFieldState.clearText()
        }
    }
    Row(
        modifier = modifier
            .focusRequester(focusRequester)
            .rippleLessClickable {
                focusRequester.requestFocus()
            }
            .fillMaxWidth()
            .clearFocusOnTap()
            .imePadding()
            .padding(LocalSpacing.current.dimen12)
            .glowingBorder()
            .clip(CircleShape)
            .background(LocalThemeColor.current.primary.p250.copy(alpha = 0.9f))
            .padding(LocalSpacing.current.dimen8),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.dimen2)
    ) {
        Icon(
            modifier = Modifier.minimumInteractiveComponentSize(),
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = LocalThemeColor.current.white.copy(alpha = 0.2f)
        )
        CreateMusicTextField(
            modifier = Modifier
                .weight(1f),
            textFieldState = textFieldState,
            interactionSource = interactionSource,
            onDone = onDone
        )
        IconButton(
            onClick = onDone,
            enabled = textFieldState.text.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Send,
                contentDescription = stringResource(R.string.create_music_text_field_send_button_description)
            )
        }
    }
}

@Composable
private fun CreateMusicTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    interactionSource: MutableInteractionSource,
    onDone: () -> Unit
) {
    BasicTextField(
        modifier = modifier,
        interactionSource = interactionSource,
        state = textFieldState,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = LocalContentColor.current
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        onKeyboardAction = {
            onDone()
        },
        decorator = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                propagateMinConstraints = true
            ) {
                if (textFieldState.text.isBlank()) {
                    Text(
                        modifier = Modifier.sharedElementForCreateText(),
                        text = stringResource(R.string.create_music_text_field_row_placeholder),
                        color = LocalThemeColor.current.white.copy(alpha = 0.2f)
                    )
                }
                innerTextField()
            }
        },
    )
}


@Preview
@Composable
private fun CreateMusicTextFieldRowPreview() {
    PreviewTheme {
        Box(
            modifier = Modifier
        ) {
            CreateMusicTextFieldRow(
                onFocusRemoved = {},
                onGenerate = {}
            )
        }

    }
}