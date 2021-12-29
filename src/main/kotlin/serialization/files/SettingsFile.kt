package serialization.files

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Settings
import resources.States
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

class SettingsFile(val path: Path) {

    fun saveToFile(states: States) {
        with(states) {
            path.writeText(Json.encodeToString(Settings(
                paths = paths as List<String>,
                lastTrack = currentTrack.value,
                shuffle = shuffle.value,
                repeat = repeat.value,
                volume = volume.value,
                theme = theme.value,
                screen = screen.value,
                songPosition = songPosition.value
            )))
        }
    }

    fun readFromFile(): Settings = Json.decodeFromString(path.readText())
}