package com.arturzarbabyan.artgallery.data.model.mapper

import com.arturzarbabyan.artgallery.data.model.response.ArtworkResponse
import com.arturzarbabyan.artgallery.domain.model.Artwork

object ArtMapper {
    fun mapToDomain(response: ArtworkResponse): Artwork {
        return Artwork(
            id = response.id,
            title = response.title,
            imageUrl = "https://www.artic.edu/iiif/2/${response.image_id}/full/300,/0/default.jpg"
        )
    }
}