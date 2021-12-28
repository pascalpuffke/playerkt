package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resources.States
import utils.loadCoverImage
import utils.withDebugBorder

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun Playlist(
    modifier: Modifier = Modifier, states: States,
) {
    Box(modifier = modifier.fillMaxSize().withDebugBorder(states)) {
        val lazyListState = rememberLazyListState()

        LazyColumn(state = lazyListState) {
            if (states.library.isEmpty()) item {
                Row(modifier = Modifier.fillMaxWidth().padding(10.dp).withDebugBorder(states)) {
                    Text("No songs found. Go to the Settings tab to configure search paths.")
                }
            }
            items(states.library) { track ->
                Row(modifier = Modifier.fillMaxWidth().clickable {
                    println("left-clicked on ${track.title}")

                    states.songPosition.value = 0
                    states.currentTrack.value = track

                    if (track.cover == null) track.cover = loadCoverImage(states, track.filePath)
                }.padding(10.dp).withDebugBorder(states)) {
                    PlaylistEntry(track = track, states = states)
                }
            }
        }

        VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd).withDebugBorder(states),
                          adapter = rememberScrollbarAdapter(lazyListState))
    }
}