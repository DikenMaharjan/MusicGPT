package com.example.musicplayer.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.feature.generate_music.ui.GenerateMusicScreen
import com.example.musicplayer.navigation.component.bottom_bar.AppBottomBar
import com.example.musicplayer.navigation.component.bottom_bar.GenerateMusicRoute
import com.example.musicplayer.ui_core.components.layout.AppContainer

@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
    AppContainer(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                AppBottomBar(
                    selectedRoute = GenerateMusicRoute
                )
            },
        ) { padding ->
            // Will be replaced by NavHost for navigation
            Box(
                modifier = modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                GenerateMusicScreen()
            }
        }
    }
}

