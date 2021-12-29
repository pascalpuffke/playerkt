package serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import resources.Screen

object ScreenSerializer : KSerializer<Screen> {
    override val descriptor = PrimitiveSerialDescriptor("Screen", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Screen) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder) = when (decoder.decodeString()) {
        "Library" -> Screen.Library
        "LogMessages" -> Screen.LogMessages
        "Settings" -> Screen.Settings
        else -> Screen.Library
    }

}