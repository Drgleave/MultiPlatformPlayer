package com.example.mpplayer.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.mpplayer.model.Track
import com.example.mpplayer.player.AudioPlayer
import com.example.mpplayer.player.PlayerState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

data class PlayingScreen(
    val position: Int,
    val player: AudioPlayer,
    val audioList: List<Track>,
    val playerState: PlayerState) : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black).padding(horizontal = 20.dp)
        ) {

            Box(
                modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp)
                    .background(Color.DarkGray.copy(alpha = 0.3f), RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.TopEnd
            ) {

                KamelImage(
                    asyncPainterResource(audioList[position].images.toString()),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().requiredHeight(300.dp)
                )

                IconButton(
                    onClick = { navigator.pop() },
                    Modifier.size(35.dp).padding(vertical = 10.dp)
                        .background(Color.Transparent.copy(alpha = 0.5f), RoundedCornerShape(15.dp))
                ) {
                    Icon(
                        painter = painterResource("path.png"),
                        contentDescription = "Go back",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }


            }

            Box(modifier = Modifier.height(height = 25.dp))

            Column(
                modifier = Modifier.height(50.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    audioList[position].name!!,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    audioList[position].artists!!,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            }

            Box(modifier = Modifier.height(height = 15.dp))

            (if (playerState.currentItemIndex >= 0) audioList[position].duration else "0:00")?.let {
                PlayerSeekBarAndDuration(
                    modifier = Modifier.background(Color.Black).padding(horizontal = 0.dp)
                        .height(70.dp),
                    isPlaying = playerState.isPlaying,
                    currentDuration = playerState.currentTime,
                    totalDurationInSeconds = playerState.duration,
                    totalDuration = it,
                    onSeek = { player.seekTo(it) }
                )
            }

            Box(modifier = Modifier.height(height = 10.dp))

            PlayerControls(
                modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                isBuffering = playerState.isBuffering,
                isPlaying = playerState.isPlaying,
                onPause = { player.pause() },
                onPlay = { player.play() },
                onNext = { player.next() },
                onPrev = { player.prev() }
            )

        }


    }

}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isBuffering: Boolean,
    isPlaying: Boolean,
    onPause: () -> Unit,
    onPlay: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F))
        IconButton(
            onClick = onPrev,
            Modifier.size(35.dp)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(15.dp))
        ) {
            Icon(
                painter = painterResource("prev.png"),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
        Box(Modifier.size(70.dp)) {
            if (!isBuffering) {
                Button(
                    onClick = { if (isPlaying) onPause() else onPlay() },
                    modifier = Modifier.size(70.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                ) {
                    Icon(
                        painter = if (isPlaying) painterResource("pause.png") else painterResource("play.png"),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                        tint = Color.White
                    )
                }
            } else {
                CircularProgressIndicator(
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        IconButton(
            onClick = onNext,
            Modifier.size(35.dp)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(15.dp))
        ) {
            Icon(
                painter = painterResource("next.png"),
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Box(modifier = Modifier.weight(1F))
    }
}

@Composable
fun PlayerSeekBarAndDuration(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    currentDuration: Long,
    totalDurationInSeconds: Long,
    totalDuration: String,
    onSeek: (Double) -> Unit
) {

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {

        Slider(
            parseDurationToFloatPlayer(
                currentDuration,
                totalDurationInSeconds,
                totalDuration,
                isPlaying
            ),
            modifier = Modifier.weight(1f),
            onValueChange = {
                val seekTime =
                    parseFloatToDurationPlayer(it, totalDurationInSeconds, totalDuration, isPlaying)
                onSeek(seekTime)
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                inactiveTrackColor = Color.Gray,
                activeTrackColor = Color.Blue
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                parseDurationToTimePlayer(currentDuration, "", false),
                fontSize = 14.sp,
                color = Color.White
            )
            Box(modifier = Modifier.weight(1F))
            Text(
                parseDurationToTimePlayer(totalDurationInSeconds, totalDuration, isPlaying),
                fontSize = 14.sp,
                color = Color.White
            )
        }

    }

}


private fun parseDurationToTimePlayer(
    totalDuration: Long,
    otherTotalDuration: String,
    isPlaying: Boolean
): String {
    val seconds =
        if (isPlaying && totalDuration == 0L) convertTimeToSecondsPlayer(otherTotalDuration) else totalDuration
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    val formattedSeconds =
        if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds.toString()
    return "$minutes:$formattedSeconds"
}

private fun parseDurationToFloatPlayer(
    currentDuration: Long,
    max: Long,
    otherMax: String,
    isPlaying: Boolean
): Float {
    val newMax = if (isPlaying && max == 0L) convertTimeToSecondsPlayer(otherMax) else max
    val percentage = (currentDuration.toFloat() / newMax.toFloat()).coerceIn(0f, 1f)
    return if (percentage.isNaN()) 0f else percentage
}

private fun parseFloatToDurationPlayer(
    value: Float,
    max: Long,
    otherMax: String,
    isPlaying: Boolean
): Double {
    val newMax = if (isPlaying && max == 0L) convertTimeToSecondsPlayer(otherMax) else max
    return (newMax * value).toDouble()
}

fun convertTimeToSecondsPlayer(time: String): Long {
    val parts = time.split(":")
    val minutes = parts[0].toLong()
    val seconds = parts[1].toLong()
    return (minutes * 60 + seconds) // total seconds
}
