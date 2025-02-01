package com.arturzarbabyan.artgallery.domain.model

data class Artwork(
    val id: Int,
    val title: String,
    val artist: String,
    val year: String,
    val medium: String,
    val type: String,
    val dimensions: String,
    val department: String,
    val imageUrl: String,
    val imageUrlBig: String
)