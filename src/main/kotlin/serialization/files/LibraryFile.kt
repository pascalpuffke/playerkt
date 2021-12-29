package serialization.files

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.json.Json
import models.Track
import serialization.SnapshotListSerializer
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.io.path.writeText

// The library file (library.json) contains a list of all tracks.
// Let's abuse a JSON file as a database!
class LibraryFile(val path: Path) {

    private val snapshotListSerializer = SnapshotListSerializer(Track.serializer())

    fun saveToFile(library: SnapshotStateList<Track>) =
        path.writeText(Json.encodeToString(snapshotListSerializer, library))

    fun readFromFile(): SnapshotStateList<Track> = Json.decodeFromString(snapshotListSerializer, path.readText())
}