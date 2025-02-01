package com.arturzarbabyan.artgallery.data.model.response

import kotlinx.serialization.Serializable


@Serializable
data class ArtsResponse(val data: List<ArtworkResponse>)