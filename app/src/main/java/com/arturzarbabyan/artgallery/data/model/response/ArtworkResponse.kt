package com.arturzarbabyan.artgallery.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkResponse(
    @SerialName("id") val id: Int = 0,
    @SerialName("title") val title: String = "Unknown Title",
    @SerialName("image_id") val image_id: String? = null
)