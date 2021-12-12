package utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.xml.sax.InputSource
import resources.States
import resources.fileTypes
import java.io.File
import java.nio.file.FileVisitOption
import java.nio.file.Files
import java.nio.file.Path
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.io.path.isRegularFile
import kotlin.math.ln
import kotlin.math.pow

// Seems convenient, but may be a bit ridiculous writing these as String extensions
fun String.loadAsImageBitmap() = loadImageBitmap(File(this).inputStream())
fun String.loadAsImageVector(density: Float = 1f) =
    loadXmlImageVector(InputSource(File(this).inputStream()), Density(density))

fun DateTimeFormatter.fromUnixTime(timestamp: Long): String =
    this.format(Date.from(Instant.ofEpochMilli(timestamp)).toInstant())

fun Modifier.withDebugBorder(states: States) = this.then(Modifier.border(states.borderStroke.value))

fun songPosToString(pos: Int): String {
    val minutes = pos / 60
    val seconds = pos % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}

fun loadCoverImage(states: States, path: Path): ImageBitmap? {
    try {
        println("Loading cover image for ${path.fileName}")
        val audioFile = AudioFileIO.read(path.toFile())
        val artwork = audioFile.tag.firstArtwork ?: return null
        return loadImageBitmap(artwork.binaryData.inputStream())
    } catch (e: Exception) {
        e.printStackTrace()
        states.messages.add(LogMessage(MessageType.Warning,
                                       "Failed to load cover image for $path",
                                       System.currentTimeMillis()))
        return null
    }
}

fun scanLibrary(states: States, path: Path): List<Track>? {
    if (Files.notExists(path)) return null

    // Why isn't this the default? Their logger annihilates stdout.
    Logger.getLogger("org.jaudiotagger").level = Level.OFF

    val tracks = mutableListOf<Track>()

    Files.walk(path, FileVisitOption.FOLLOW_LINKS).filter(Path::isRegularFile)
        .filter { it.fileName.toString().split('.').last() in fileTypes }.map {
            try {
                return@map AudioFileIO.read(it.toFile())
            } catch (e: Exception) {
                states.messages.add(LogMessage(MessageType.Warning,
                                               "Failed to load file $it",
                                               System.currentTimeMillis()))
                return@map null
            }
        }.filter { it != null }.map { it as AudioFile }
        .sorted(Comparator.comparing { it.tag.getFirst(FieldKey.ARTIST) }).forEach {
            val file = it.file
            val audioFile = AudioFileIO.read(file)
            val header = audioFile.audioHeader
            val tag = audioFile.tag
            val year = tag.getFirst(FieldKey.YEAR)
            val track = Track(title = tag.getFirst(FieldKey.TITLE).ifEmpty { file.nameWithoutExtension },
                              artist = tag.getFirst(FieldKey.ARTIST).ifEmpty { "Unknown artist" },
                              album = tag.getFirst(FieldKey.ALBUM).ifEmpty { "Unknown album" },
                              genre = tag.getFirst(FieldKey.GENRE).ifEmpty { "Unknown genre" },
                              year = if (year.contains('-')) year.split('-').first()
                                  .toIntOrNull() else year.toIntOrNull(),
                              track = tag.getFirst(FieldKey.TRACK).toIntOrNull(),
                              duration = header.trackLength,
                              bitDepth = header.bitsPerSample,
                              sampleRate = header.sampleRateAsNumber,
                              bitrate = header.bitRateAsNumber,
                              size = file.length(),
                              filePath = file.toPath())

            tracks.add(track)
            states.messages.add(LogMessage(MessageType.Info,
                                           "Found new track: ${track.filePath}",
                                           System.currentTimeMillis()))
        }

    return tracks.toList()
}

fun rescanLibrary(states: States) {
    states.messages.add(LogMessage(MessageType.Info, "Re-scanning library", System.currentTimeMillis()))

    states.library.filter { Files.notExists(it.filePath) }.forEach {
        states.messages.add(LogMessage(MessageType.Info,
                                       "Removed: ${it.filePath.fileName}",
                                       System.currentTimeMillis()))
        states.library.remove(it)
    }

    states.paths.map { Path.of(it) }.forEach { path ->
        scanLibrary(states, path)?.filter { it !in states.library }?.forEach {
            states.messages.add(LogMessage(MessageType.Info,
                                           "Found new track: ${it.filePath.fileName}",
                                           System.currentTimeMillis()))
            states.library.add(it)
        }
    }
}

fun toggleBorders(states: States): Boolean {
    // Borders are currently enabled
    if (states.borderStroke.value.width == 1.dp) {
        states.borderStroke.value = BorderStroke(0.dp, Color.Transparent)
        return true
    }
    states.borderStroke.value = BorderStroke(1.dp, states.theme.value.error)
    return false
}

object Memory {
    private val runtime = Runtime.getRuntime()

    fun format(memory: Long): String {
        val unit = 1024
        if (memory < unit) return "$memory B"
        val exp = (ln(memory.toDouble()) / ln(unit.toDouble())).toInt()
        val pre = "KMGTPE"[exp - 1] + "i"
        return String.format("%.2f %sB", memory / unit.toDouble().pow(exp), pre)
    }

    fun total(): Long = runtime.totalMemory()
    fun free(): Long = runtime.freeMemory()
    fun max(): Long = runtime.maxMemory()
}

val jvmName =
    "${System.getProperty("java.vm.vendor")} ${System.getProperty("java.vm.name")} ${System.getProperty("java.vm.version")}"