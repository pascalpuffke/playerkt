package models

import kotlinx.serialization.Serializable
import resources.Screen
import resources.Theme
import serialization.ScreenSerializer
import serialization.ThemeSerializer

@Serializable
data class Settings(
    val paths: List<String>,
    val lastTrack: Track?,
    val shuffle: Boolean,
    val repeat: Boolean,
    val volume: Float,
    @Serializable(with = ThemeSerializer::class) val theme: Theme,
    @Serializable(with = ScreenSerializer::class) val screen: Screen,
    var songPosition: Int,
)
