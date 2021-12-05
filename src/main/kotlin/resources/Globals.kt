package resources

import player.Player
import utils.loadAsImageBitmap
import java.nio.file.Path

val fileTypes = arrayOf("mp3", "wav", "flac")
val libraryPath = Path.of(System.getProperty("user.home"), "Music", "testing")
val albumCover = "res/rick.jpg".loadAsImageBitmap()
val player = Player(false, false)