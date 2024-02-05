package com.example.mpplayer.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("artists")
    val artists: String?,
    @SerialName("duration")
    val duration: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("images")
    val images: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("uri")
    val uri: String?
)