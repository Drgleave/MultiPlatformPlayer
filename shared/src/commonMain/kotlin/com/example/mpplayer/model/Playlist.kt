package com.example.mpplayer.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Playlist (
    @SerialName("id")
    val id: String?,
    @SerialName("images")
    val images: String?,
    @SerialName("name")
    val name: String?
)