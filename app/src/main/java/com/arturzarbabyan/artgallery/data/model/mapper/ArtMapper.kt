package com.arturzarbabyan.artgallery.data.model.mapper

import com.arturzarbabyan.artgallery.data.model.response.ArtworkResponse
import com.arturzarbabyan.artgallery.domain.model.Artwork

object ArtMapper {
    fun mapToDomain(response: ArtworkResponse): Artwork {
        return Artwork(
            id = response.id,
            title = response.title,
            artist = response.artist ?: "Unknown Artist",
            year = response.year ?: "Unknown Year",
            medium = response.medium ?: "Unknown Medium",
            type = response.type ?: "Unknown Type",
            dimensions = response.dimensions ?: "Unknown Dimensions",
            department = response.department ?: "Unknown Category",
            imageUrl = response.imageUrl,
            imageUrlBig = response.imageUrl,
        )
    }
}