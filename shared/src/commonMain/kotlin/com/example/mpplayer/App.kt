package com.example.mpplayer



import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.mpplayer.navigation.RootComponent
import com.example.mpplayer.screens.DashboardScreen
import com.example.mpplayer.screens.PlayerScreen
import com.example.mpplayer.screens.PlaylistScreen
import com.example.mpplayer.screens.SearchScreen


@Composable
fun App(root: RootComponent) {

    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Children(
            stack = childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when(val instance = child.instance) {
                is RootComponent.Child.ScreenA -> DashboardScreen(instance.component)
                is RootComponent.Child.ScreenB -> PlayerScreen(instance.component.text, instance.component)
                is RootComponent.Child.ScreenC -> PlaylistScreen(instance.component.text, instance.component)
                is RootComponent.Child.ScreenD -> SearchScreen(instance.component.text, instance.component)
                else -> {}
            }
        }
    }
}