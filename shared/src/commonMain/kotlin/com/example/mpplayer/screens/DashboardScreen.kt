package com.example.mpplayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.example.mpplayer.model.DashboardViewModel
import com.example.mpplayer.model.Playlist
import com.example.mpplayer.model.Track
import com.example.mpplayer.navigation.ScreenAComponent
import com.example.mpplayer.navigation.ScreenAEvent
import com.example.mpplayer.player.AudioPlayer
import com.example.mpplayer.player.rememberPlayerState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DashboardScreen (component: ScreenAComponent) {

    val text by component.text.subscribeAsState()
    val searchText by component.searchText.subscribeAsState()

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Screen A")
        OutlinedTextField(
            value = text,
            onValueChange = { component.onEvent(ScreenAEvent.UpdateText(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Button(onClick = { component.onEvent(ScreenAEvent.ClickButtonA) }) {
            Text("Go to Screen B")
        }
    }

    component.onEvent(ScreenAEvent.UpdateText("0"))

    var playPos = 0
    MaterialTheme {
        val viewModel = remember { DashboardViewModel() }
        val audioList = remember { viewModel.getAudios() }
        val playList = remember { viewModel.getPlaylists() }
        val playerState = rememberPlayerState()
        val player = remember { AudioPlayer(playerState) }

        LaunchedEffect(Unit){
            player.addSongsUrls(audioList.map { it.uri!! }) }

        (if (playerState.currentItemIndex >= 0) audioList[playerState.currentItemIndex].duration else "0:00")?.let {

            Scaffold() {
                Column(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f))){
                    Box(modifier = Modifier.background(Color.Black.copy(alpha = 0.5f))) {
                        Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)){
                            Text("RadioHead",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White)
                            Box(modifier = Modifier.weight(1F))
                            Image(painter = painterResource("3.png"),
                                contentDescription = null,
                                modifier = Modifier.padding(8.dp).size(40.dp).clip(RoundedCornerShape(25.dp)))
                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().background(Color.Black).padding(15.dp), contentAlignment = Alignment.CenterEnd) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {  },
                            modifier = Modifier.fillMaxWidth().height(40.dp).background(Color.DarkGray.copy(alpha = 0.8f), RoundedCornerShape(18.dp))
                        )

                        Image(painter = painterResource("loupe.png"),
                            contentDescription = null,
                            modifier = Modifier.padding(18.dp).size(16.dp))
                    }

                    MusicList(audioList = audioList, onClick = { player.play(it) }, currentPlayingIndex = playerState.currentItemIndex)

//                    Playlist(
//                        playLists = playList,
//                        isPlaying = playerState.isPlaying,
//                        onPause = { player.pause() },
//                        onPlay = { player.play()}) { component.onEvent(ScreenAEvent.ClickButtonA) }

                    Box(modifier = Modifier.fillMaxWidth().background(Color.Black), contentAlignment = Alignment.CenterStart) {
                        PlaylistItem(playList[0], playerState.isPlaying, playlistSize = playList.size.toString(), { player.pause() }, { player.play()}){
                            //component.onEvent(ScreenAEvent.ClickButtonA)
                        }
                    }

                    if (playerState.isPlaying || playerState.isBuffering) {
                        component.onEvent(ScreenAEvent.UpdateText(playerState.currentItemIndex.toString()))
                        playPos = playerState.currentItemIndex
                    }

                    Column(modifier = Modifier.fillMaxWidth().background(Color.Black).padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                        Box(modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 10.dp)
                            .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(35.dp))
                            .clickable(onClick = { component.onEvent(ScreenAEvent.ClickButtonA) })) {

                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 0.dp, end = 10.dp)) {
                                KamelImage(
                                    asyncPainterResource(audioList[playPos].images.toString()),
                                    contentDescription = null,
                                    modifier = Modifier.padding(10.dp).size(50.dp).clip(RoundedCornerShape(35.dp))
                                )
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)){
                                    Text(audioList[playPos].name!!, fontSize = 16.sp, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text(audioList[playPos].artists!!, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.White)
                                }
                                Box(Modifier.size(50.dp)){
                                    if (!playerState.isBuffering){
                                        Button(onClick = { if (playerState.isPlaying) { player.pause() } else { player.play() } },
                                            modifier = Modifier.size(70.dp),
                                            shape = RoundedCornerShape(25.dp),
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                                        ){
                                            Icon(
                                                painter = if (playerState.isPlaying) painterResource("pause.png") else painterResource("play.png"),
                                                contentDescription = "",
                                                modifier = Modifier.size(50.dp),
                                                tint = Color.White
                                            )
                                        }
                                    }else{
                                        CircularProgressIndicator(color = Color.Blue, modifier = Modifier.align(Alignment.Center))
                                    }
                                }

                            }

                        }

                    }

                    Box(modifier = Modifier.height(30.dp).background(Color.Black).fillMaxWidth()){}

                }

            }

        }

        DisposableEffect(Unit){
            onDispose {
                player.cleanUp()
            }
        }

    }


}

@Composable
fun ColumnScope.MusicList(
    modifier: Modifier = Modifier,
    audioList: List<Track>,
    currentPlayingIndex: Int,
    onClick: (Int) -> Unit){
    val state = rememberLazyListState()
    Column(modifier = modifier.fillMaxWidth().weight(1f).background(Color.Black)){
        LazyColumn(state = state) {
            itemsIndexed(audioList){ index, audio ->
                TrackItem(audio, index == currentPlayingIndex){
                    onClick(index)
                }
            }
        }
    }
    LaunchedEffect(currentPlayingIndex){
        if (currentPlayingIndex != -1) {
            // Check if item at index 5 is visible
            val visibleItems = state.layoutInfo.visibleItemsInfo
            val itemIsVisible = visibleItems.any { it.index == currentPlayingIndex }
            if (!itemIsVisible) {
                state.animateScrollToItem(currentPlayingIndex)
            }
        }
    }
}

@Composable
fun TrackItem(
    audio: Track,
    isPlaying: Boolean,
    onClick: () -> Unit){
    val color = if (isPlaying) Color.Blue.copy(alpha = 0.9f) else Color.DarkGray.copy(alpha = 0.5f)
    val backgroundColor = if (isPlaying) Color.Blue else Color.DarkGray.copy(alpha = 0.3f)
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)){
        KamelImage(
            asyncPainterResource(audio.images!!),
            contentDescription = null,
            modifier = Modifier.padding(10.dp).size(50.dp).clip(RoundedCornerShape(15.dp))
        )
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)){
            Text(audio.name!!, fontSize = 16.sp, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(audio.artists!!, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.White)
        }
        Text(
            audio.duration!!,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.padding(10.dp).background(color, RoundedCornerShape(5.dp)).padding(3.dp)
        )
    }
}


@Composable
fun Playlist(
    modifier: Modifier = Modifier,
    playLists: List<Playlist>,
    isPlaying: Boolean,
    onPause: () -> Unit,
    onPlay: () -> Unit,
    onClick: (Int) -> Unit){
    Box(modifier = modifier) {
        val state = rememberLazyListState()
        Column(modifier = modifier.width(150.dp).height(150.dp).background(Color.Black)){
            LazyColumn(state = state) {
                itemsIndexed(playLists){ index, audio ->
                    PlaylistItem(audio, isPlaying, playlistSize = playLists.size.toString(), onPause, onPlay){
                        onClick(index)
                    }
                }
            }
        }
        LaunchedEffect(isPlaying){
            if (isPlaying) {
                val visibleItems = state.layoutInfo.visibleItemsInfo
                val itemIsVisible = visibleItems.any { it.index == 0 }
                if (!itemIsVisible) {
                    state.animateScrollToItem(0)
                }
            }
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlaylistItem(
    playlist: Playlist,
    isPlaying: Boolean,
    playlistSize: String,
    onPause: () -> Unit,
    onPlay: () -> Unit,
    onClick: () -> Unit){
    val color = if (isPlaying) Color.Blue.copy(alpha = 0.9f) else Color.DarkGray.copy(alpha = 0.5f)
    val backgroundColor = if (isPlaying) Color.Blue else Color.DarkGray
    Box(modifier = Modifier.clickable(onClick = onClick).size(200.dp).padding(vertical = 20.dp, horizontal = 10.dp)
        .background(Color.DarkGray, RoundedCornerShape(25.dp))) {
        KamelImage(
            asyncPainterResource(playlist.images.toString()),
            contentDescription = null,
            modifier = Modifier.matchParentSize().padding(4.dp)
        )

        Row(modifier = Modifier.width(150.dp).align(alignment = Alignment.BottomCenter).background(backgroundColor, RoundedCornerShape(15.dp)).padding(5.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)){
                Text(playlist.name!!, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("$playlistSize Tracks", fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Color.White)
            }

            Box(Modifier.size(30.dp).padding(1.dp)) {
                Button(
                    onClick = { if (isPlaying) onPause() else onPlay() },
                    modifier = Modifier.size(30.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Icon(
                        painter = if (isPlaying) painterResource("pause.png") else painterResource("play.png"),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.White
                    )
                }

            }
            
        }

    }
}


