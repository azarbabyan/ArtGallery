package com.arturzarbabyan.artgallery.domain.usecase

import androidx.paging.PagingData
import com.arturzarbabyan.artgallery.data.repository.ArtRepository
import com.arturzarbabyan.artgallery.domain.model.Artwork
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtworksUseCase @Inject constructor(
    private val repository: ArtRepository
) {
    operator fun invoke(classification: String,searchQuery: String?): Flow<PagingData<Artwork>> = repository.getArtworks(classification,searchQuery)
}