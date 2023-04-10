package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap

class Radio(
    val title: String,
    var imageBitmap: ImageBitmap? = null,
    var tracks: List<Song> = emptyList(),
)
