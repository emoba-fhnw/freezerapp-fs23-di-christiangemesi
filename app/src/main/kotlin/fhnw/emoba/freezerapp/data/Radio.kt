package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap

class Radio(
    val title: String,
    val image: String,
    val tracklist: String,
    var isFavorite: Boolean,
    var imageBitmap: ImageBitmap? = null // new property to hold the downloaded image

) {

    override fun toString(): String {
        return "RadioStation(title='$title', picture='$image', tracklist='$tracklist', isFavorite=$isFavorite)"
    }
}