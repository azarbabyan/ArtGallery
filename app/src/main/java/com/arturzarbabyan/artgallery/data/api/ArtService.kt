package com.arturzarbabyan.artgallery.data.api

import com.arturzarbabyan.artgallery.data.model.response.ArtResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtService {
    @GET("artworks")
    suspend fun getArtworks(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "id,title,image_id"
    ): ArtResponse
}