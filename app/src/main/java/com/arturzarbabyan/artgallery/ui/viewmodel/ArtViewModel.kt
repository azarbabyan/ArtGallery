package com.arturzarbabyan.artgallery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arturzarbabyan.artgallery.domain.model.Artwork
import com.arturzarbabyan.artgallery.domain.usecase.GetArtworksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val getArtworksUseCase: GetArtworksUseCase
) : ViewModel() {

    private val _selectedClassification = MutableStateFlow("Painting") // Default filter
    val selectedClassification: StateFlow<String> = _selectedClassification
    private val _artworksFlow = MutableStateFlow<Flow<PagingData<Artwork>>>(emptyFlow())

    val artworksFlow: StateFlow<Flow<PagingData<Artwork>>> = _artworksFlow

    private val _searchQuery = MutableStateFlow<String?>(null) // Search term
    val searchQuery: StateFlow<String?> = _searchQuery

    init {
        refreshArtworks() // Load default category
    }



    fun updateClassification(newClassification: String) {
        if (_selectedClassification.value == newClassification) return
        _selectedClassification.value = newClassification
        refreshArtworks()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        refreshArtworks()
    }

    private fun refreshArtworks() {
        _artworksFlow.value = flowOf(PagingData.empty())

        viewModelScope.launch {
            _artworksFlow.value = getArtworksUseCase(
                classification = _selectedClassification.value,
                searchQuery = _searchQuery.value
            ).cachedIn(viewModelScope)
        }
    }

}