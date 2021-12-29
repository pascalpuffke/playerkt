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
        path.writeText(Json.encodeToString(states.toSettings()))
    }

    fun readFromFile(): Settings = Json.decodeFromString(path.readText())
}