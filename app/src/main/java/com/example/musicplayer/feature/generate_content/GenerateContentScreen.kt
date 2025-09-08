package com.example.musicplayer.feature.generate_content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musicplayer.feature.generate_content.component.GenerateContentTopBar

@Composable
fun GenerateContentScreen(
    modifier: Modifier = Modifier,
    viewModel: GenerateContentScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            GenerateContentTopBar()
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            
        }
    }
}