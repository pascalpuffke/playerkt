import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import components.ClickableRow
import components.ControlBar
import components.TrackBar
import resources.*
import screens.LibraryScreen
import screens.LogMessagesScreen
import screens.SettingsScreen
import utils.rescanLibrary
import utils.toggleBorders
import utils.withDebugBorder
import java.awt.Dimension

@Composable
fun App(states: States) {
    MaterialTheme(states.theme.value.colors, Fonts.fonts) {
        Surface(Modifier.fillMaxSize()) {
            // A Scaffold might make sense here, but it enforces some things I don't like

            Column(modifier = Modifier.withDebugBorder(states), verticalArrangement = Arrangement.SpaceBetween) {
                Row(Modifier.height(48.dp)) {
                    ControlBar(modifier = Modifier.fillMaxSize(), states = states)
                }

                Box {
                    Row(Modifier.height(150.dp).align(Alignment.BottomEnd)) {
                        TrackBar(modifier = Modifier.fillMaxSize().shadow(96.dp), states = states)
                    }

                    // Without the padding it would overflow
                    Row(Modifier.padding(bottom = 150.dp)) {
                        if (states.window.size.width >= 800.dp) {
                            Column(modifier = Modifier.width(250.dp).fillMaxHeight().withDebugBorder(states),
                                   horizontalAlignment = Alignment.CenterHorizontally) {
                                ClickableRow(modifier = Modifier.fillMaxWidth(),
                                             states = states,
                                             icon = Icons.Black.home,
                                             onClick = {}) {
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
                                ClickableRow(modifier = Modifier.fillMaxWidth(),
                                             states = states,
                                             icon = Icons.Black.queue,
                                             onClick = {}) {
                                    Text("Playlists")
                                }
                                ClickableRow(modifier = Modifier.fillMaxWidth().padding(start = 12.dp),
                                             states = states,
                                             icon = Icons.Black.add,
                                             onClick = {}) {
                                    Text("Create new...")
                                }
                            }
                        }
                        Surface(color = states.theme.value.backgroundContrast) {
                            when (states.screen.value) {
                                Screen.Library -> LibraryScreen(states)
                                Screen.LogMessages -> LogMessagesScreen(states)
                                Screen.Settings -> SettingsScreen(states)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val windowState = rememberWindowState(size = DpSize(650.dp, 650.dp))
    Window(onCloseRequest = ::exitApplication,
           title = "Stupid music player that doesn't even play music",
           state = windowState) {
        // Smaller than that and we get problems.
        window.minimumSize = with(LocalDensity.current) { Dimension(650.dp.roundToPx(), 300.dp.roundToPx()) }

        val states = States(library = remember { mutableStateListOf() },
                            paths = remember { mutableStateListOf() },
                            messages = remember { mutableStateListOf() },
                            currentTrack = remember { mutableStateOf(null) },
                            playing = remember { mutableStateOf(false) },
                            shuffle = remember { mutableStateOf(false) },
                            repeat = remember { mutableStateOf(false) },
                            volume = remember { mutableStateOf(0.69f) },
                            theme = remember { mutableStateOf(Themes.default) },
                            screen = remember { mutableStateOf(Screens.default) },
                            borderStroke = remember { mutableStateOf(BorderStroke(0.dp, Color.Transparent)) },
                            songPosition = remember { mutableStateOf(0) },
                            window = windowState)

        states.paths.add(""/*libraryPath.toString()*/)
        // for (path in states.paths) if (path.isNotEmpty()) states.library.addAll(scanLibrary(Path.of(path)) ?: emptyList())

        MenuBar {
            Menu("File", mnemonic = 'F') {
                Menu("Export...", mnemonic = 'E') {
                    Item("Library (.csv)", icon = rememberVectorPainter(Icons.Black.fileDownload)) {}
                    Item("Playlist (.m3u)", icon = rememberVectorPainter(Icons.Black.fileDownload)) {}
                }

                Item("Rescan library", mnemonic = 'R', icon = rememberVectorPainter(Icons.Black.refresh)) {
                    rescanLibrary(states)
                }

                Separator()

                Item("Exit", mnemonic = 'x', icon = rememberVectorPainter(Icons.Black.exit)) {
                    exitApplication()
                }

                Separator()
            }

            Menu("Playback", mnemonic = 'P') {
                Item("Play", shortcut = KeyShortcut(Key.Spacebar), icon = rememberVectorPainter(Icons.Black.play)) {
                    states.playing.value = !states.playing.value

                    if (player.playing) player.pause()
                    else player.play()
                }

                Item("Stop", icon = rememberVectorPainter(Icons.Black.stop)) {
                    states.playing.value = false
                    states.songPosition.value = 0
                    states.currentTrack.value = null

                    player.stop()
                }

                Item("Next",
                     shortcut = KeyShortcut(Key.DirectionRight, shift = true),
                     icon = rememberVectorPainter(Icons.Black.next)) {
                    player.next()
                }

                Item("Previous",
                     shortcut = KeyShortcut(Key.DirectionLeft, shift = true),
                     icon = rememberVectorPainter(Icons.Black.previous)) {
                    player.previous()
                }

                Separator()

                CheckboxItem("Shuffle",
                             mnemonic = 'S',
                             checked = states.shuffle.value,
                             shortcut = KeyShortcut(Key.S, shift = true),
                             icon = rememberVectorPainter(Icons.Black.shuffle)) {
                    states.shuffle.value = it
                    player.toggleShuffle()
                    assert(player.shuffle == it)
                }

                CheckboxItem("Repeat",
                             mnemonic = 'R',
                             checked = states.repeat.value,
                             shortcut = KeyShortcut(Key.R, shift = true),
                             icon = rememberVectorPainter(Icons.Black.repeat)) {
                    states.repeat.value = it
                    player.toggleRepeat()
                    assert(player.repeat == it)
                }

                Separator()
            }

            Menu("Settings", mnemonic = 'S') {
                Menu("Theme", mnemonic = 'T') {
                    Item("Currently selected: ${states.theme.value}",
                         icon = rememberVectorPainter(Icons.Black.palette)) {}
                    Separator()
                    for (theme in Theme.values()) {
                        Item(theme.toString(),
                             icon = rememberVectorPainter(if (theme.colors.isLight) Icons.Black.lightMode else Icons.Black.darkMode)) {
                            states.theme.value = theme
                        }
                    }
                }

                Menu("Debugging", mnemonic = 'D') {
                    CheckboxItem("Draw UI boundaries",
                                 checked = states.borderStroke.value.width == 1.dp,
                                 shortcut = KeyShortcut(Key.B, ctrl = true),
                                 icon = rememberVectorPainter(Icons.Black.bug)) {
                        if (states.borderStroke.value.width == 1.dp) {
                            states.borderStroke.value = BorderStroke(1.dp, states.theme.value.error)
                        }
                        toggleBorders(states)
                    }
                }
                Separator()
            }
        }

        App(states)
    }
}
