package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap

class Radio(
    val title: String,
    var isFavorite: Boolean,
    var imageBitmap: ImageBitmap? = null,
    var tracks: List<String> = emptyList()
) {

    override fun toString(): String {
        return "RadioStation(title='$title', isFavorite=$isFavorite)"
    }
}
