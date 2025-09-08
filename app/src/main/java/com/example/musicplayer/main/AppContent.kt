package com.example.musicplayer.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.musicplayer.feature.generate_content.GenerateContentScreen
import com.example.musicplayer.navigation.component.bottom_bar.AppBottomBar
import com.example.musicplayer.navigation.component.bottom_bar.GenerateContentRoute

@Composable
fun AppContent(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomBar(
                selectedRoute = GenerateContentRoute
            )
        }
    ) { padding ->
        // Will be replaced by NavHost for navigation
        Box(
            modifier = modifier.padding(padding)
        ) {
            GenerateContentScreen()
        }
    }
}

