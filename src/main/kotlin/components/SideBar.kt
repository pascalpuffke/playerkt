import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.ClickableRow
import components.ExpandableRow
import components.SingleLineText
import models.Playlist
import resources.Icons
import resources.Screen
import resources.States
import utils.withDebugBorder

@Composable
fun NewPlaylist(
    states: States,
    createPlaylistState: MutableState<Boolean>,
) {
    var playlistName by remember { mutableStateOf("Playlist ${states.playlists.size + 1}") }

    Column(modifier = Modifier.padding(start = 12.dp, end = 5.dp).withDebugBorder(states)) {
        Row(modifier = Modifier.fillMaxWidth().height(48.dp).withDebugBorder(states)) {
            TextField(modifier = Modifier.fillMaxSize(),
                      value = playlistName,
                      onValueChange = { playlistName = it },
                      singleLine = true,
                      shape = RectangleShape,
                      textStyle = LocalTextStyle.current.copy(fontSize = 12.sp))
        }
        Row(modifier = Modifier.fillMaxWidth().height(48.dp).withDebugBorder(states).padding(5.dp)) {
            Column(modifier = Modifier.fillMaxWidth(.5f).fillMaxHeight().withDebugBorder(states)) {
                Button(modifier = Modifier.fillMaxSize(),
                       shape = RectangleShape,
                       colors = ButtonDefaults.buttonColors(backgroundColor = states.theme.value.secondary),
                       onClick = {
                           if (playlistName.isNotEmpty()) {
                               createPlaylistState.value = false
                               states.playlists += Playlist(playlistName)
                           }
                       }) {
                    SingleLineText("Create")
                }
            }
            Column(modifier = Modifier.fillMaxSize().withDebugBorder(states)) {
                Button(modifier = Modifier.fillMaxSize(),
                       shape = RectangleShape,
                       colors = ButtonDefaults.buttonColors(backgroundColor = states.theme.value.primary),
                       onClick = {
                           createPlaylistState.value = false
                       }) {
                    SingleLineText("Cancel")
                }
            }
        }
    }
}

@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    states: States,
) {
    val scrollState = rememberScrollState()
    val createPlaylistState = remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxHeight().verticalScroll(scrollState).withDebugBorder(states),
               horizontalAlignment = Alignment.CenterHorizontally) {
            ClickableRow(modifier = Modifier.fillMaxWidth(), states = states, icon = Icons.Black.home, onClick = {
                states.screen.value = Screen.Library
            }) {
                SingleLineText("Home")
            }
            Spacer(modifier = Modifier.height(12.dp))
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                SingleLineText("Tracks")
            }
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                SingleLineText("Albums")
            }
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                SingleLineText("Artists")
            }
            Spacer(modifier = Modifier.height(12.dp))
            ExpandableRow(modifier = Modifier.fillMaxWidth(), states = states, innerContent = {
                SingleLineText("Playlists")
            }) {
                Column {
                    for (playlist in states.playlists) {
                        ClickableRow(modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                                     states = states,
                                     icon = Icons.Black.queue,
                                     onClick = {
                                         states.screen.value = Screen.Library
                                     }) {
                            SingleLineText(playlist.name)
                        }
                    }

                    ClickableRow(modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                                 states = states,
                                 icon = Icons.Black.add,
                                 onClick = { createPlaylistState.value = !createPlaylistState.value }) {
                        SingleLineText("Create new...")
                    }

                    AnimatedVisibility(visible = createPlaylistState.value) {
                        NewPlaylist(states = states, createPlaylistState = createPlaylistState)
                    }
                }
            }
        }

        VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd).withDebugBorder(states),
                          adapter = rememberScrollbarAdapter(scrollState))
    }
}
