package serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import resources.Theme
import resources.Themes

object ThemeSerializer : KSerializer<Theme> {
    override val descriptor = PrimitiveSerialDescriptor("Theme", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Theme) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder) = when (decoder.decodeString()) {
        "Gruvbox Dark" -> Theme.GruvboxDark
        "Gruvbox Light" -> Theme.GruvboxLight
        "Ayu Dark" -> Theme.AyuDark
        "Ayu Mirage" -> Theme.AyuMirage
        "Ayu Light" -> Theme.AyuLight
        else -> Themes.default
    }
}