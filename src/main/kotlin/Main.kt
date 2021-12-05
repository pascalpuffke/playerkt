import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import components.Playlist
import components.TrackBar
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import resources.*
import utils.Track
import java.nio.file.FileVisitOption
import java.nio.file.Files
import java.nio.file.Path
import java.util.logging.Level
import java.util.logging.Logger

fun scanLibrary(path: Path): List<Track>? {
    if (Files.notExists(path)) return null

    // Why isn't this the default? Their logger annihilates stdout.
    Logger.getLogger("org.jaudiotagger").level = Level.OFF

    val tracks = mutableListOf<Track>()

    Files.walk(path, FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile)
        .filter { it.fileName.toString().split('.').last() in fileTypes }.forEach {
            val file = it.toFile()
            val audioFile = AudioFileIO.read(file)
            val header = audioFile.audioHeader
            val tag = audioFile.tag
            val year = tag.getFirst(FieldKey.YEAR)

            tracks.add(Track(title = tag.getFirst(FieldKey.TITLE) ?: "unknown",
                             artist = tag.getFirst(FieldKey.ARTIST) ?: "unknown",
                             album = tag.getFirst(FieldKey.ALBUM) ?: "unknown",
                             genre = tag.getFirst(FieldKey.GENRE) ?: "unknown",
                             year = if (year.contains('-')) year.split('-').first()
                                 .toIntOrNull() else year.toIntOrNull(),
                             track = tag.getFirst(FieldKey.TRACK).toIntOrNull(),
                             duration = header.trackLength,
                             bitDepth = header.bitsPerSample,
                             sampleRate = header.sampleRateAsNumber,
                             bitrate = header.bitRateAsNumber,
                             size = file.length(),
                             file = it))

            println("Found track ${tracks.last()}")
        }

    return tracks.toList()
}

@Composable
fun App(states: States) {
    if (states.library.isEmpty()) states.library.addAll(scanLibrary(libraryPath) ?: emptyList())

    MaterialTheme(states.theme.value.colors, Fonts.fonts) {
        Surface(Modifier.fillMaxSize()) {
            Column(modifier = Modifier.border(states.borderStroke.value),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.SpaceBetween) {
                Box {
                    Row(Modifier.height(150.dp).align(Alignment.BottomEnd)) {
                        TrackBar(modifier = Modifier.fillMaxSize().shadow(96.dp), states = states)
                    }

                    Row(Modifier.fillMaxSize().align(Alignment.TopStart)) {
                        Playlist(states = states)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication,
           title = "Stupid music player that doesn't even play music",
           state = WindowState(size = DpSize(700.dp, 700.dp))) {
        val states = States(library = remember { mutableStateListOf() },
                            currentTrack = remember { mutableStateOf(null) },
                            playing = remember { mutableStateOf(false) },
                            shuffle = remember { mutableStateOf(false) },
                            repeat = remember { mutableStateOf(false) },
                            volume = remember { mutableStateOf(0.69f) },
                            theme = remember { mutableStateOf(Themes.default) },
                            borderStroke = remember { mutableStateOf(BorderStroke(0.dp, Color.Transparent)) },
                            songPosition = remember { mutableStateOf(0) })

        MenuBar {
            Menu("File", mnemonic = 'F') {
                Menu("Export...", mnemonic = 'E') {
                    Item("Library (.csv)", icon = rememberVectorPainter(Icons.Black.fileDownload)) {}
                    Item("Playlist (.m3u)", icon = rememberVectorPainter(Icons.Black.fileDownload)) {}
                }

                Item("Rescan library", mnemonic = 'R', icon = rememberVectorPainter(Icons.Black.refresh)) {
                    println("Re-scanning library")

                    states.library.filter { Files.notExists(it.file) }.forEach {
                        println("Removing: ${it.file.fileName}")
                        states.library.remove(it)
                    }

                    scanLibrary(libraryPath)?.filter { it !in states.library }?.forEach {
                        println("Found new track: ${it.file.fileName}")
                        states.library.add(it)
                    }
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
                        states.borderStroke.value =
                            if (it) BorderStroke(1.dp, states.theme.value.error) else BorderStroke(0.dp,
                                                                                                   Color.Transparent)
                    }
                }
                Separator()
            }
        }

        App(states)
    }
}
