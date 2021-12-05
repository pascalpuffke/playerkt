package resources

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import java.io.File

object Fonts {
    // best font, don't @ me
    val fonts = Typography(
        FontFamily(
            Font(File("res/Cantarell-Regular.ttf"), FontWeight(400), FontStyle.Normal),
            Font(File("res/Cantarell-Italic.ttf"), FontWeight(400), FontStyle.Italic)
        )
    )
}