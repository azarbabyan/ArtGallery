package com.arturzarbabyan.artgallery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.arturzarbabyan.artgallery.domain.model.Artwork
import com.arturzarbabyan.artgallery.ui.viewmodel.ArtViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen(viewModel: ArtViewModel, navController: NavController) {
    val classifications = listOf("Painting", "Sculpture", "Drawing", "Print", "Photography", "Textile")
    val selectedClassification by viewModel.selectedClassification.collectAsState()
    val artworksFlow by viewModel.artworksFlow.collectAsState()
    val artworks: LazyPagingItems<Artwork> = artworksFlow.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) } // Controls search visibility
    var searchText by remember { mutableStateOf(searchQuery ?: "") } // Local search state

    Column {
        TopAppBar(
            title = {
                if (isSearchExpanded) {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        placeholder = { Text("Search Artworks") },
                        leadingIcon = {
                            IconButton(onClick = {
                                isSearchExpanded = false
                                searchText = "" // Reset when closed
                                viewModel.updateSearchQuery("") // Clear results
                            }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.updateSearchQuery(searchText) // Perform search
                            }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }
                    )
                } else {
                    Text("Art Gallery")
                }
            },
            actions = {
                if (!isSearchExpanded) {
                    IconButton(onClick = { isSearchExpanded = true }) {
                        Icon(Icons.Filled.Search, contentDescription = "Search")
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.padding(8.dp)) {
            Button(onClick = { expanded = true }) {
                Text(text = "Filter: $selectedClassification")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                classifications.forEach { classification ->
                    DropdownMenuItem(
                        onClick = {
                            viewModel.updateClassification(classification)
                            expanded = false
                        },
                        text = {  Text(text = classification)}
                    )

                }
            }
        }
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
            // Show loading indicator when new data is loading
            when (artworks.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp))
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error loading paintings.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            color = Color.Red
                        )
                    }
                }
                else -> {}
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
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = art.imageUrl,
                    placeholder = null,
                    error = null
                ),
                contentDescription = art.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = art.title,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(4.dp)
            )
        }
    }

}