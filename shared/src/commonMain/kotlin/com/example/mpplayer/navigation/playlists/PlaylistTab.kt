package com.example.mpplayer.navigation.playlists

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition

object PlaylistTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Playlist"
            val icon = rememberVectorPainter(Icons.Default.PlayArrow)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(screen = PlaylistsScreen()) { navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
