package com.example.mpplayer



import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.mpplayer.navigation.NavigationScreens
import com.example.mpplayer.navigation.RootComponent
import com.example.mpplayer.navigation.home.HomeTab
import com.example.mpplayer.navigation.playlists.PlaylistTab
import com.example.mpplayer.screens.DashboardScreen
import com.example.mpplayer.screens.PlayerScreen
import com.example.mpplayer.navigation.playlists.PlaylistsScreen
import com.example.mpplayer.navigation.search.SearchScreen
import com.example.mpplayer.navigation.search.SearchTab


@Composable
fun App(root: RootComponent) {

    MaterialTheme {

//
//        val childStack by root.childStack.subscribeAsState()
//        Children(
//            stack = childStack,
//            animation = stackAnimation(slide())) { child ->
//            when(val instance = child.instance) {
//                is RootComponent.Child.ScreenA -> DashboardScreen(instance.component)
//                is RootComponent.Child.ScreenB -> PlayerScreen(instance.component.text, instance.component)
//                is RootComponent.Child.ScreenC -> PlaylistsScreen()
//                is RootComponent.Child.ScreenD -> SearchScreen()
//                else -> {}
//            }
//
//        }

        TabNavigator(tab = HomeTab) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigation (backgroundColor = Color.DarkGray, contentColor = Color.White){
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(SearchTab)
                        TabNavigationItem(PlaylistTab)
                    }
                },
                content = { CurrentTab() },
            )
        }

//        Text(
//            "RadioHead",
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )

    }

}


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.Black,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title
            )
        }
    )
}
