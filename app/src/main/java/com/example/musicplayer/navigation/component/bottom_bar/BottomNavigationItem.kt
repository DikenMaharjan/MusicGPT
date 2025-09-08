package com.example.musicplayer.navigation.component.bottom_bar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.musicplayer.R

@Immutable
data class BottomNavigationItem(
    val route: Any,
    val icon: Painter,
    val contentDescription: String,
)


val bottomNavigationItems
    @Composable
    get() = listOf(
        BottomNavigationItem(
            route = GenerateMusicRoute,
            icon = painterResource(R.drawable.generate_ai_icon),
            contentDescription = stringResource(R.string.bottom_bar_generate_ai_icon_description)
        ),
        BottomNavigationItem(
            route = TempRoute,
            icon = painterResource(R.drawable.discover_icon),
            contentDescription = stringResource(R.string.bottom_bar_discover_icon_description)
        ),
        BottomNavigationItem(
            route = TempRoute,
            icon = painterResource(R.drawable.reel_icon),
            contentDescription = stringResource(R.string.bottom_bar_reel_icon_description)
        ),
        BottomNavigationItem(
            route = TempRoute,
            icon = painterResource(R.drawable.user_icon),
            contentDescription = stringResource(R.string.bottom_bar_user_icon_description)
        )
    )


// A temporary route for each screen till each screen is implemented
data object TempRoute

// A temporary route for generate content screen
data object GenerateMusicRoute