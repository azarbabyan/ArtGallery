package com.arturzarbabyan.artgallery.di

import com.arturzarbabyan.artgallery.data.api.ArtService
import com.arturzarbabyan.artgallery.data.repository.ArtRepository
import com.arturzarbabyan.artgallery.data.repository.ArtRepositoryImpl
import com.arturzarbabyan.artgallery.domain.usecase.GetArtworksUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.artic.edu/api/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideArtService(retrofit: Retrofit): ArtService =
        retrofit.create(ArtService::class.java)

    @Provides
    @Singleton
    fun provideArtRepository(api: ArtService): ArtRepository =
        ArtRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideGetArtworksUseCase(repository: ArtRepository): GetArtworksUseCase =
        GetArtworksUseCase(repository)
}