package utils

import androidx.compose.ui.graphics.ImageBitmap
import java.nio.file.Path

data class Track(
    val title: String,
    val artist: String? = null,
    val album: String? = null,
    val genre: String? = null,
    val year: Int? = null,
    val track: Int? = null,
    val duration: Int,
    val bitDepth: Int,
    val sampleRate: Int,
    val bitrate: Long,
    val size: Long,
    val filePath: Path,
    var cover: ImageBitmap? = null,
)
