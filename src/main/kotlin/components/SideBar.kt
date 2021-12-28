import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import components.ClickableRow
import resources.Icons
import resources.States
import utils.withDebugBorder

@Composable
fun SideBar(
    modifier: Modifier = Modifier,
    width: Dp = 250.dp,
    states: States,
) {
    val scrollState = rememberScrollState()

    Box {
        Column(modifier = modifier.width(width).fillMaxHeight().verticalScroll(scrollState).withDebugBorder(states),
               horizontalAlignment = Alignment.CenterHorizontally) {
            ClickableRow(modifier = Modifier.fillMaxWidth(), states = states, icon = Icons.Black.home, onClick = {}) {
                Text("Home")
            }
            Spacer(modifier = Modifier.height(12.dp))
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                Text("Tracks")
            }
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                Text("Albums")
            }
            ClickableRow(modifier = Modifier.fillMaxWidth(),
                         states = states,
                         icon = Icons.Black.musicNote,
                         onClick = {}) {
                Text("Artists")
            }
            Spacer(modifier = Modifier.height(12.dp))
            ClickableRow(modifier = Modifier.fillMaxWidth(), states = states, icon = Icons.Black.queue, onClick = {}) {
                Text("Playlists")
            }
            ClickableRow(modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                         states = states,
                         icon = Icons.Black.add,
                         onClick = {}) {
                Text("Create new...")
            }
        }

        VerticalScrollbar(modifier = Modifier.align(Alignment.CenterEnd).withDebugBorder(states),
                          adapter = rememberScrollbarAdapter(scrollState))
    }
}
