package com.arturzarbabyan.artgallery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.arturzarbabyan.artgallery.domain.model.Artwork
import com.arturzarbabyan.artgallery.ui.viewmodel.ArtViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen(viewModel: ArtViewModel, navController: NavController) {
    val artworks: LazyPagingItems<Artwork> = viewModel.artFlow.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = { Text("Art Gallery") },
            actions = {
                IconButton(onClick = { /* TODO: Implement Search */ }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

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
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate("detail/${art.id}") }
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(art.imageUrl),
                contentDescription = art.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = art.title, fontSize = 16.sp, color = Color.Black)
        }
    }
}