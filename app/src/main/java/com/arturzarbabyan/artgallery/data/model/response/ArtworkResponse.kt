package com.arturzarbabyan.artgallery.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("artist_display") val artist: String? = "Unknown Artist",
    @SerialName("date_display") val year: String? = "Unknown Year",
    @SerialName("medium_display") val medium: String? = "Unknown Medium",
    @SerialName("classification_title") val type: String? = "Unknown Type",
    @SerialName("dimensions") val dimensions: String? = "Unknown Dimensions",
    @SerialName("department_title") val department: String? = "Unknown Category",
    @SerialName("image_id") val imageId: String?
){
    val imageUrl: String
        get() = imageId?.let { "https://www.artic.edu/iiif/2/$it/full/300,/0/default.jpg" }
            ?: "https://via.placeholder.com/800"
    val imageUrlBig: String
        get() = imageId?.let { "https://www.artic.edu/iiif/2/$it/full/1024,/0/default.jpg" }
            ?: "https://via.placeholder.com/800"
}