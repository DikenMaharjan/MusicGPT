package com.example.musicplayer.feature.generate_music.ui.component.create_music

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.musicplayer.ui.theme.LocalSpacing

@Composable
fun CreateMusicView(
    modifier: Modifier = Modifier,
    generateMusic: (String) -> Unit
) {
    var showTextField by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember {
        FocusRequester()
    }
    AnimatedContent(
        modifier = modifier,
        targetState = showTextField
    ) { isTextFieldVisible ->
        if (isTextFieldVisible) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
            CreateMusicTextFieldRow(
                modifier = Modifier
                    .imePadding()
                    .focusRequester(focusRequester),
                onFocusRemoved = {
                    showTextField = false
                },
                onGenerate = generateMusic
            )
        } else {
            CreateMusicButton(
                modifier = Modifier.padding(LocalSpacing.current.dimen12),
                onClick = {
                    showTextField = true
                }
            )
        }
    }
}