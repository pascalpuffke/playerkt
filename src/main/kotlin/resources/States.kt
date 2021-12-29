package resources

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.window.WindowState
import models.LogMessage
import models.Playlist
import models.Track

/**
 * Helper class to manage all data related to the state of the application.
 * This might not be the best way to do this, but what do I know.
 * Certainly much better than passing around a bunch of MutableStates between components.
 */
data class States(
    var library: SnapshotStateList<Track>,
    var paths: SnapshotStateList<String>,
    var messages: SnapshotStateList<LogMessage>,
    var playlists: SnapshotStateList<Playlist>,
    var currentTrack: MutableState<Track?>,
    val playing: MutableState<Boolean>,
    val shuffle: MutableState<Boolean>,
    val repeat: MutableState<Boolean>,
    val volume: MutableState<Float>,
    val theme: MutableState<Theme>,
    val screen: MutableState<Screen>,
    val borderStroke: MutableState<BorderStroke>,
    var songPosition: MutableState<Int>,
    val window: WindowState,
)
