package resources

import player.Player
import utils.loadAsImageBitmap
import java.nio.file.Path

const val artist = "Rick Astley"
const val title = "Never Gonna Give You Up"
const val album = "Whenever You Need Somebody"
const val length = 215

val fileTypes = arrayOf("mp3", "wav", "flac")
val libraryPath = Path.of(System.getProperty("user.home"), "Music", "testing")
val albumCover = "res/rick.jpg".loadAsImageBitmap()
val player = Player(false, false)