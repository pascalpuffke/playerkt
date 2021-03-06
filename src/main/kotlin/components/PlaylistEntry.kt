package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Track
import resources.Icons
import resources.States
import utils.withDebugBorder

@Composable
fun PlaylistEntry(
    modifier: Modifier = Modifier,
    track: Track,
    states: States,
) {
    Box(modifier.fillMaxWidth().withDebugBorder(states)) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            val color = if (states.currentTrack.value == track) {
                states.theme.value.primaryVariant
            } else {
                states.theme.value.onSurface
            }

            Column(modifier = Modifier.fillMaxWidth(.33f).withDebugBorder(states)) {
                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.withDebugBorder(states)) {
                        Icon(imageVector = Icons.White.musicNote, tint = color, contentDescription = null)
                    }
                    Spacer(Modifier.width(8.dp))
                    Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
                        SingleLineText(text = track.title, color = color)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth(.33f).withDebugBorder(states)) {
                Row {
                    SingleLineText(text = track.artist ?: "Unknown artist", color = color)
                }
            }

            Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
                Row {
                    SingleLineText(text = track.album ?: "Unknown album", color = color)
                }
            }
        }
    }
}