package resources

import player.Player
import serialization.files.LibraryFile
import serialization.files.PlaylistsFile
import serialization.files.SettingsFile
import utils.loadAsImageBitmap
import java.nio.file.Path

val fileTypes = arrayOf("mp3", "wav", "flac")
val defaultAlbumCover = "res/rick.jpg".loadAsImageBitmap()
val player = Player(shuffle = false, repeat = false)

object Serializers {
    val libraryFilePath: Path = Path.of("library.json")
    val playlistsFilePath: Path = Path.of("playlists.json")
    val settingsFilePath: Path = Path.of("settings.json")

    val librarySerializer = LibraryFile(libraryFilePath)
    val playlistsSerializer = PlaylistsFile(playlistsFilePath)
    val settingsSerializer = SettingsFile(settingsFilePath)
}