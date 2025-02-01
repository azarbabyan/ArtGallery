package com.arturzarbabyan.artgallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.arturzarbabyan.artgallery.data.api.ArtService
import com.arturzarbabyan.artgallery.data.model.mapper.ArtMapper
import com.arturzarbabyan.artgallery.data.paging.ArtPagingSource
import com.arturzarbabyan.artgallery.domain.model.Artwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val api: ArtService
) : ArtRepository {
    override fun getArtworks(): Flow<PagingData<Artwork>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { ArtPagingSource(api) }
    ).flow.map { pagingData ->
        pagingData.map { ArtMapper.mapToDomain(it) }
    }
}