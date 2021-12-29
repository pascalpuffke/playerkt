package models

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import serialization.PathSerializer
import java.nio.file.Path

@Serializable
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
    @Serializable(with = PathSerializer::class) val filePath: Path,
    @Transient var cover: ImageBitmap? = null,
)
