package com.arturzarbabyan.artgallery.data.api

import com.arturzarbabyan.artgallery.data.model.response.ArtResponse
import com.arturzarbabyan.artgallery.data.model.response.ArtsResponse
import com.arturzarbabyan.artgallery.data.model.response.ArtworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtService {
    @GET("artworks")
    suspend fun getArtworks(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "id,title,image_id"
    ): ArtsResponse

    @GET("artworks/search")
    suspend fun getPaintings(
        @Query("classification_titles") classification: String,
        @Query("q") searchQuery: String?,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
        @Query("fields") fields: String = "id,title,image_id,artist_display,date_display"
    ): ArtsResponse

    @GET("artworks/{id}")
    suspend fun getArtworkDetails(@Path("id") id: Int): ArtResponse
}