package components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import resources.Icons
import resources.States
import utils.Track
import utils.withDebugBorder

@Composable
fun PlaylistEntry(
    modifier: Modifier = Modifier,
    track: Track,
    states: States,
) {
    Box(modifier.fillMaxWidth().withDebugBorder(states)) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Column(modifier = Modifier.fillMaxWidth(.33f).withDebugBorder(states)) {
                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.withDebugBorder(states)) {
                        Icon(imageVector = Icons.White.musicNote, contentDescription = null)
                    }
                    Spacer(Modifier.width(8.dp))
                    Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
                        Text(text = track.title, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    }
                }
            }

            Column(modifier = Modifier.fillMaxWidth(.33f).withDebugBorder(states)) {
                Row {
                    Text(text = track.artist ?: "Unknown artist", overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }

            Column(modifier = Modifier.fillMaxWidth().withDebugBorder(states)) {
                Row {
                    Text(text = track.album ?: "Unknown album", overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }
        }
    }
}