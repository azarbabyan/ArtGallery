package com.arturzarbabyan.artgallery.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.arturzarbabyan.artgallery.ui.viewmodel.DetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(artId: Int, onBackClick: () -> Unit) {
    val viewModel: DetailViewModel = hiltViewModel()
    val artwork by viewModel.artDetail.collectAsState()
    var showFullScreen by remember { mutableStateOf(false) }

    // Fetch artwork details
    LaunchedEffect(artId) {
        viewModel.getArtworkDetails(artId)
    }

    Column {
        TopAppBar(
            title = { Text("Artwork Details") },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        AnimatedVisibility(visible = artwork != null, enter = fadeIn(), exit = fadeOut()) {
            artwork?.let { art ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(art.imageUrlBig),
                        contentDescription = art.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                            .clickable{showFullScreen = true },
                        contentScale = ContentScale.FillBounds
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = art.title,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(text = "Artist: ${art.artist}", fontSize = 18.sp, color = Color.Gray)
                    Text(text = "Year: ${art.year}", fontSize = 18.sp, color = Color.Gray)
                    Text(text = "Medium: ${art.medium}", fontSize = 18.sp, color = Color.Gray)
                    Text(text = "Type: ${art.type}", fontSize = 18.sp, color = Color.Gray)
                    Text(text = "Dimensions: ${art.dimensions}", fontSize = 18.sp, color = Color.Gray)
                    Text(text = "Category: ${art.department}", fontSize = 18.sp, color = Color.Gray)
                    if (showFullScreen) {
                        FullScreenImage(art.imageUrlBig) { showFullScreen = false }
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

    }
}