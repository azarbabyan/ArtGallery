package com.arturzarbabyan.artgallery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter

@Composable
fun FullScreenImage(imageUrl: String, onClose: () -> Unit) {
    Dialog(onDismissRequest = { onClose() }) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Full Screen Image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { onClose() },
                contentScale = ContentScale.Fit
            )
        }
    }
}
