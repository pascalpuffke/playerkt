package serialization.files

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Playlist
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

// The playlists file (playlists.json) contains a list of all playlists,
// with their names and tracks they contain.
class PlaylistsFile(val path: Path) {

    fun saveToFile(playlists: List<Playlist>) = path.writeText(Json.encodeToString(playlists))

    fun readFromFile(): List<Playlist> = Json.decodeFromString(path.readText())
}