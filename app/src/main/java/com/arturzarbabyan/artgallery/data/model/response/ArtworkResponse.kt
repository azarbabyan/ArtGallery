package com.arturzarbabyan.artgallery.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ArtworkResponse(
    val id: Int,
    val title: String,
    val image_id: String?
)