package models

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val name: String,
    val tracks: MutableList<Track>,
)
