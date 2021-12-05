package utils

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.unit.Density
import org.xml.sax.InputSource
import resources.States
import java.io.File

// Seems convenient, but may be a bit ridiculous writing these as String extensions
fun String.loadAsImageBitmap() = loadImageBitmap(File(this).inputStream())
fun String.loadAsImageVector(density: Float = 1f) =
    loadXmlImageVector(InputSource(File(this).inputStream()), Density(density))

fun Modifier.withDebugBorder(states: States) = this.then(Modifier.border(states.borderStroke.value))

fun songPosToString(pos: Int): String {
    val minutes = pos / 60
    val seconds = pos % 60
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}