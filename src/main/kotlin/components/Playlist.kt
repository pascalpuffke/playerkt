package components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resources.States

@Composable
@Preview
fun Playlist(
    modifier: Modifier = Modifier, states: States,
) {
    Box(modifier.fillMaxWidth().border(states.borderStroke.value)) {
        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState
        ) {
            items(states.library.size) { i ->
                val track = states.library[i]
                Row(modifier = Modifier.fillMaxWidth().clickable {
                    println("clicked on ${track.title}")

                    states.songPosition.value = 0
                    states.currentTrack.value = track
                }.padding(10.dp).border(states.borderStroke.value)) {
                    PlaylistEntry(track = track, states = states)
                }
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).border(states.borderStroke.value),
            adapter = rememberScrollbarAdapter(lazyListState)
        )
    }
}