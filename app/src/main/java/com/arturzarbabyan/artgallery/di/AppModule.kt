package com.arturzarbabyan.artgallery.di

import android.content.Context
import com.arturzarbabyan.artgallery.data.api.ArtService
import com.arturzarbabyan.artgallery.data.repository.ArtRepository
import com.arturzarbabyan.artgallery.data.repository.ArtRepositoryImpl
import com.arturzarbabyan.artgallery.domain.usecase.GetArtworksUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.SocketFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    fun providesOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        Json.serializersModule
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(httpLoggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY })
            .cache(Cache(context.cacheDir, 100 * 1024 * 1024))
            .connectTimeout(45_000L, TimeUnit.MILLISECONDS)
            .readTimeout(45_000L, TimeUnit.MILLISECONDS)
            .writeTimeout(45_000L, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json,client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
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