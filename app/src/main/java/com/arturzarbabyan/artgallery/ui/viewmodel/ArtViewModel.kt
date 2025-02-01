package com.arturzarbabyan.artgallery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arturzarbabyan.artgallery.domain.model.Artwork
import com.arturzarbabyan.artgallery.domain.usecase.GetArtworksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val getArtworksUseCase: GetArtworksUseCase
) : ViewModel() {

    val artFlow: Flow<PagingData<Artwork>> = getArtworksUseCase().cachedIn(viewModelScope)
}