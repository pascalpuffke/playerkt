package components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import resources.Icons
import resources.States
import resources.defaultAlbumCover
import resources.player
import utils.songPosToString
import utils.withDebugBorder
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackBar(
    modifier: Modifier = Modifier,
    states: States,
) {
    Surface(color = states.theme.value.backgroundContrast, modifier = modifier) {
        Row(Modifier.withDebugBorder(states)) {
            Column(Modifier.withDebugBorder(states)) {
                Image(
                    bitmap = states.currentTrack.value?.cover ?: defaultAlbumCover,
                    contentDescription = null,
                )
            }

            Column(modifier = Modifier.background(Brush.horizontalGradient(0.0f to states.theme.value.backgroundContrast,
                                                                           1.0f to states.theme.value.background))
                .fillMaxSize().padding(10.dp).withDebugBorder(states), verticalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.fillMaxHeight(.33f).withDebugBorder(states)) {
                    if (states.currentTrack.value != null) {
                        Row {
                            SingleLineText(states.currentTrack.value!!.title)
                        }
                        Row {
                            SingleLineText(buildAnnotatedString {
                                append(states.currentTrack.value!!.artist ?: "Unknown artist")
                                withStyle(SpanStyle(states.theme.value.textContrast)) {
                                    append(" - ")
                                    append(states.currentTrack.value!!.album ?: "Unknown album")
                                }
                            })
                        }
                    } else {
                        Row {
                            SingleLineText("Nothing playing :(", color = states.theme.value.textContrast)
                        }
                    }
                }

                //Row {
                Box {
                    Column(
                        modifier = Modifier.fillMaxWidth(.2f).withDebugBorder(states),
                    ) {
                        Row(modifier = Modifier.padding(horizontal = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            TooltipArea(
                                tooltip = {
                                    Surface(Modifier.background(states.theme.value.background,
                                                                RoundedCornerShape(10.dp))) {
                                        SingleLineText("${(states.volume.value * 100).roundToInt()}%")
                                    }
                                },
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center) {
                                    Icon(imageVector = if (states.volume.value < .5f) Icons.White.volumeLow else Icons.White.volumeHigh,
                                         contentDescription = null,
                                         modifier = Modifier.withDebugBorder(states))
                                    Slider(value = states.volume.value,
                                           valueRange = 0.0f..1.0f,
                                           onValueChange = {
                                               states.volume.value = it
                                           },
                                           modifier = Modifier.offset(x = 24.dp).fillMaxWidth().withDebugBorder(states),
                                           colors = SliderDefaults.colors(activeTrackColor = states.theme.value.secondary,
                                                                          thumbColor = states.theme.value.textContrast))
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states),
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            modifier = Modifier.padding(5.dp).fillMaxHeight(.55f).withDebugBorder(states),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(icon = Icons.White.shuffle,
                                       modifier = Modifier.fillMaxHeight(.75f).withDebugBorder(states),
                                       iconPadding = 8.dp,
                                       colors = ButtonDefaults.buttonColors(backgroundColor = if (states.shuffle.value) states.theme.value.primary else states.theme.value.backgroundContrast)) {
                                states.shuffle.value = !states.shuffle.value
                                player.toggleShuffle()
                                assert(states.shuffle.value == player.shuffle)
                            }
                            Spacer(Modifier.width(20.dp))
                            IconButton(icon = Icons.White.previous,
                                       modifier = Modifier.fillMaxHeight(.9f).withDebugBorder(states),
                                       iconPadding = 8.dp,
                                       colors = ButtonDefaults.buttonColors(backgroundColor = states.theme.value.background)) {
                                states.songPosition.value = 0
                                player.previous()
                            }
                            Spacer(Modifier.width(10.dp))
                            IconButton(
                                icon = if (states.playing.value) Icons.White.pause else androidx.compose.material.icons.Icons.Default.PlayArrow,
                                modifier = Modifier.withDebugBorder(states).shadow(10.dp, CircleShape)
                            ) {
                                states.playing.value = !states.playing.value
                                player.pause()
                            }
                            Spacer(Modifier.width(10.dp))
                            IconButton(icon = Icons.White.next,
                                       modifier = Modifier.fillMaxHeight(.9f).withDebugBorder(states),
                                       iconPadding = 8.dp,
                                       colors = ButtonDefaults.buttonColors(backgroundColor = states.theme.value.background)) {
                                states.songPosition.value = states.currentTrack.value?.duration ?: 0
                                player.next()
                            }
                            Spacer(Modifier.width(20.dp))
                            IconButton(icon = Icons.White.repeat,
                                       modifier = Modifier.fillMaxHeight(.75f).withDebugBorder(states),
                                       iconPadding = 8.dp,
                                       colors = ButtonDefaults.buttonColors(backgroundColor = if (states.repeat.value) states.theme.value.primary else states.theme.value.backgroundContrast)) {
                                states.repeat.value = !states.repeat.value
                                player.toggleRepeat()
                                assert(states.repeat.value == player.repeat)
                            }
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth().withDebugBorder(states),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    SingleLineText(songPosToString(states.songPosition.value), color = states.theme.value.textContrast)
                    Spacer(Modifier.padding(4.dp))
                    if (states.currentTrack.value == null) {
                        Slider(value = 0f,
                               valueRange = 0.0f..0.0f,
                               onValueChange = {},
                               colors = SliderDefaults.colors(thumbColor = states.theme.value.textContrast),
                               modifier = Modifier.fillMaxWidth(.875f).withDebugBorder(states),
                               enabled = false)
                    } else {
                        Slider(value = states.songPosition.value.toFloat(),
                               valueRange = 0.0f..states.currentTrack.value!!.duration.toFloat(),
                               onValueChange = {
                                   states.songPosition.value = it.toInt()
                               },
                               colors = SliderDefaults.colors(thumbColor = states.theme.value.textContrast),
                               modifier = Modifier.fillMaxWidth(.875f).withDebugBorder(states))
                    }
                    Spacer(Modifier.padding(4.dp))
                    SingleLineText(songPosToString(states.currentTrack.value?.duration ?: 0),
                                   color = states.theme.value.textContrast)
                }
            }
        }
    }
}