package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap

class Song(
    val title: String,
    val artist: String,
    val preview: String,
    var imageBitmap: ImageBitmap? = null,
    var isFavorite: Boolean
) {
    override fun toString(): String {
        return "Song(title='$title', artist='$artist', preview='$preview', imageBitmap=$imageBitmap, isFavorite=$isFavorite)"
    }
}
