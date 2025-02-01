package com.arturzarbabyan.artgallery.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arturzarbabyan.artgallery.data.api.ArtService
import com.arturzarbabyan.artgallery.data.model.response.ArtworkResponse
import retrofit2.HttpException
import java.io.IOException

class ArtPagingSource(
    private val service: ArtService,
    private val classification: String,
    private val searchQuery: String?
) : PagingSource<Int, ArtworkResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtworkResponse> {
        val page = params.key ?: 1
        return try {
            val response = service.getPaintings(
                classification = classification,
                searchQuery = searchQuery?.takeIf { it.isNotBlank() },
                page = page,
                limit = params.loadSize)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isNotEmpty()) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArtworkResponse>): Int? = null
}