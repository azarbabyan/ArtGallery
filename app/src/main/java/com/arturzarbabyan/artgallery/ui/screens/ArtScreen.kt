package com.arturzarbabyan.artgallery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.arturzarbabyan.artgallery.domain.model.Artwork
import com.arturzarbabyan.artgallery.ui.viewmodel.ArtViewModel


@Composable
fun ArtScreen(viewModel: ArtViewModel, navController: NavController) {
    val artworks: LazyPagingItems<Artwork> = viewModel.artFlow.collectAsLazyPagingItems()

    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(artworks.itemCount) { index ->
                val art = artworks[index]
                art?.let {
                    ArtItem(art, navController)
                }
            }
        }
    }
}

@Composable
fun ArtItem(art: Artwork, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("detail/${art.id}") }
    ) {
        Image(
            painter = rememberAsyncImagePainter(art.imageUrl),
            contentDescription = art.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(text = art.title, modifier = Modifier.padding(4.dp))
    }
}