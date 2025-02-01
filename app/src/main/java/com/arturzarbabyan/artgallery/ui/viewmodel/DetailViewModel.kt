package com.arturzarbabyan.artgallery.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturzarbabyan.artgallery.data.repository.ArtRepository
import com.arturzarbabyan.artgallery.domain.model.Artwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArtRepository
) : ViewModel() {

    private val _artDetail = MutableStateFlow<Artwork?>(null)
    val artDetail: StateFlow<Artwork?> = _artDetail

    fun getArtworkDetails(artId: Int) {
        viewModelScope.launch {
            val artwork = repository.getArtworkById(artId)
            _artDetail.value = artwork
        }
    }
}