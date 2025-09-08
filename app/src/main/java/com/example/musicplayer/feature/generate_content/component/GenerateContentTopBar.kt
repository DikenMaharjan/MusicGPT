package com.example.musicplayer.feature.generate_content.component

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.R
import com.example.musicplayer.ui_core.insets.rememberZeroWindowInsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateContentTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.generate_content_top_bar_app_logo_description)
            )
        },
        title = {
            Text(stringResource(R.string.generate_content_top_bar_app_title))
        },
        windowInsets = rememberZeroWindowInsets()
    )
}

@Preview
@Composable
private fun GenerateContentTopBarPreview() {
    GenerateContentTopBar()
}