package resources

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme

enum class Theme(val colors: Colors, val backgroundContrast: Color, val textContrast: Color) {
    GruvboxDark(
        Colors(primary = Color(0xFFD65D0E),
               primaryVariant = Color(0xFFFE8019),
               secondary = Color(0xFF689D6A),
               secondaryVariant = Color(0xFF689D6A),
               background = Color(0xFF282828),
               surface = Color(0xFF282828),
               error = Color(0xFFCC241D),
               onPrimary = Color(0xFF282828),
               onSecondary = Color(0xFF282828),
               onBackground = Color(0xFFEBDBB2),
               onSurface = Color(0xFFEBDBB2),
               onError = Color(0xFF282828),
               isLight = false),
        backgroundContrast = Color(0xFF1D2021),
        textContrast = Color(0xFFA89984),
    ),
    GruvboxLight(
        Colors(primary = Color(0xFFD65D0E),
               primaryVariant = Color(0xFFAF3A03),
               secondary = Color(0xFF689D6A),
               secondaryVariant = Color(0xFF427B58),
               background = Color(0xFFF9F5D7),
               surface = Color(0xFFF9F5D7),
               error = Color(0xFFCC241D),
               onPrimary = Color(0xFFF9F5D7),
               onSecondary = Color(0xFFF9F5D7),
               onBackground = Color(0xFF3C3836),
               onSurface = Color(0xFF3C3836),
               onError = Color(0xFFF9F5D7),
               isLight = true),
        backgroundContrast = Color(0xFFFBF1C7),
        textContrast = Color(0xFF7C6F64),
    ),
    AyuDark(
        Colors(primary = Color(0xFFFF8F40), // syntax.keyword
               primaryVariant = Color(0xFFE6B673), // syntax.special
               secondary = Color(0xFF59C2FF), // syntax.entity
               secondaryVariant = Color(0xFF95E6CB), // syntax.regexp
               background = Color(0xFF131721), // editor.line
               surface = Color(0xFF131721), // editor.line
               error = Color(0xFFD95757), // common.error
               onPrimary = Color(0xFF131721), // editor.line
               onSecondary = Color(0xFF131721), // editor.line
               onBackground = Color(0xFFBFBDB6), // editor.fg
               onSurface = Color(0xFFBFBDB6), // editor.fg
               onError = Color(0xFF131721), // editor.line
               isLight = false),
        backgroundContrast = Color(0xFF0B0E14), // ui.bg
        textContrast = Color(0xFF565B66), // ui.fg
    ),
    AyuMirage(
        Colors(primary = Color(0xFFFFAD66), // syntax.keyword
               primaryVariant = Color(0xFFFFDFB3), // syntax.special
               secondary = Color(0xFF73D0FF), // syntax.entity
               secondaryVariant = Color(0xFF95E6CB), // syntax.regexp
               background = Color(0xFF1F2430), // editor.line
               surface = Color(0xFF1F2430), // editor.line
               error = Color(0xFFFF6666), // common.error
               onPrimary = Color(0xFF1F2430), // editor.line
               onSecondary = Color(0xFF1F2430), // editor.line
               onBackground = Color(0xFFCCCAC2), // editor.fg
               onSurface = Color(0xFFCCCAC2), // editor.fg
               onError = Color(0xFF1F2430), // editor.line
               isLight = false),
        backgroundContrast = Color(0xFF1A1F29), // ui.bg
        textContrast = Color(0xFF707A8C), // ui.fg
    ),
    AyuLight(
        Colors(primary = Color(0xFFFA8D3E), // syntax.keyword
               primaryVariant = Color(0xFFE6BA7E), // syntax.special
               secondary = Color(0xFF399EE6), // syntax.entity
               secondaryVariant = Color(0xFF4CBF99), // syntax.regexp
               background = Color(0xFFFCFCFC), // editor.bg
               surface = Color(0xFFFCFCFC), // editor.bg
               error = Color(0xFFE65050), // common.error
               onPrimary = Color(0xFFFCFCFC), // editor.bg
               onSecondary = Color(0xFFFCFCFC), // editor.bg
               onBackground = Color(0xFF5C6166), // editor.fg
               onSurface = Color(0xFF5C6166), // editor.fg
               onError = Color(0xFFFCFCFC), // editor.bg
               isLight = true),
        backgroundContrast = Color(0xFFEDEFF1),
        textContrast = Color(0xFF8A9199), // ui.fg
    );

    // Avoids having to type '.colors' every time
    val primary = this.colors.primary
    val primaryVariant = this.colors.primaryVariant
    val secondary = this.colors.secondary
    val secondaryVariant = this.colors.secondaryVariant
    val background = this.colors.background
    val surface = this.colors.surface
    val error = this.colors.error
    val onPrimary = this.colors.onPrimary
    val onSecondary = this.colors.onSecondary
    val onBackground = this.colors.onBackground
    val onSurface = this.colors.onSurface
    val onError = this.colors.onError

    // Maybe it would be better to include a theme name in the enum instead of
    // overriding the toString() method. Whatever.
    override fun toString(): String {
        return when (this) {
            GruvboxDark -> "Gruvbox Dark"
            GruvboxLight -> "Gruvbox Light"
            AyuDark -> "Ayu Dark"
            AyuMirage -> "Ayu Mirage"
            AyuLight -> "Ayu Light"
            else -> this.name
        }
    }
}

object Themes {
    val default = if (currentSystemTheme == SystemTheme.DARK) Theme.AyuMirage else Theme.AyuLight
}