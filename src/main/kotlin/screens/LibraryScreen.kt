package screens

import androidx.compose.runtime.Composable
import components.Playlist
import resources.States

@Composable
fun LibraryScreen(
    states: States,
) {
    Playlist(states = states)
}