package com.arturzarbabyan.artgallery.data.repository

import androidx.paging.PagingData
import com.arturzarbabyan.artgallery.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun getArtworks(): Flow<PagingData<Artwork>>
}