package com.example.musicplayer.navigation.component.bottom_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.ui.theme.LocalSpacing
import com.example.musicplayer.ui.theme.LocalThemeColor

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    selectedRoute: Any
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(LocalThemeColor.current.primary.p50)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalSpacing.current.dimen1)
                .background(LocalThemeColor.current.primary.p250)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.dimen24)
                .padding(horizontal = LocalSpacing.current.dimen8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomNavigationItems.forEach { item ->
                BottomNavigationIcon(
                    isSelected = selectedRoute == item.route,
                    icon = item.icon,
                    contentDescription = item.contentDescription,
                    onSelected = {}
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    icon: Painter,
    contentDescription: String,
    onSelected: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onSelected
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) LocalThemeColor.current.white else LocalThemeColor.current.primary.p200
        )
    }
}


@Preview
@Composable
private fun AppBottomBarPreview() {
    AppBottomBar(
        selectedRoute = GenerateContentRoute
    )
}
