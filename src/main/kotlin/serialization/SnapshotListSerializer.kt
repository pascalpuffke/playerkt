package serialization

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SnapshotListSerializer<T>(private val dataSerializer: KSerializer<T>) : KSerializer<SnapshotStateList<T>> {
    override val descriptor = ListSerializer(dataSerializer).descriptor

    override fun serialize(encoder: Encoder, value: SnapshotStateList<T>) =
        encoder.encodeSerializableValue(ListSerializer(dataSerializer), value as List<T>)

    override fun deserialize(decoder: Decoder): SnapshotStateList<T> = mutableStateListOf<T>().apply {
        addAll(decoder.decodeSerializableValue(ListSerializer(dataSerializer)))
    }
}