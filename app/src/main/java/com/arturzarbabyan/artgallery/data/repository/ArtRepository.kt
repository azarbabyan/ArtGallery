package com.arturzarbabyan.artgallery.data.repository

import androidx.paging.PagingData
import com.arturzarbabyan.artgallery.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun getArtworks(classification: String,searchQuery: String?): Flow<PagingData<Artwork>>
    suspend fun getArtworkById(id: Int): Artwork
}