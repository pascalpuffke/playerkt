package resources

import player.Player
import utils.loadAsImageBitmap

val fileTypes = arrayOf("mp3", "wav", "flac")
val defaultAlbumCover = "res/rick.jpg".loadAsImageBitmap()
val player = Player(false, false)