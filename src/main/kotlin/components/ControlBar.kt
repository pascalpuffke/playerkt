package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import resources.Icons
import resources.Screen
import resources.States
import utils.withDebugBorder

@Composable
fun PaneEntry(
    screen: Screen,
    icon: ImageVector,
    states: States,
) {
    Column(Modifier.fillMaxHeight().aspectRatio(1f).withDebugBorder(states)
               .background(if (states.screen.value == screen) states.theme.value.surface else states.theme.value.backgroundContrast)
               .clickable {
                   states.screen.value = screen
               }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon,
             tint = if (states.screen.value == screen) states.theme.value.secondary else states.theme.value.onSurface,
             contentDescription = screen.name)
    }
}

@Composable
fun ControlBar(
    modifier: Modifier = Modifier,
    states: States,
) {
    Surface(modifier = modifier, color = states.theme.value.backgroundContrast) {
        Row(Modifier.withDebugBorder(states)) {
            PaneEntry(screen = Screen.Library, icon = Icons.White.queue, states = states)
            PaneEntry(screen = Screen.LogMessages, icon = Icons.White.feed, states = states)
            PaneEntry(screen = Screen.Settings, icon = Icons.White.settings, states = states)
        }
    }
}