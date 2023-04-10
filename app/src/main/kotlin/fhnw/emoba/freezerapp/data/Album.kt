package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap

class Album(
    val title: String,
    val artist: String,
    var imageBitmap: ImageBitmap? = null,
    var tracks: List<Song> = emptyList(),
) {

    @Override
    override fun toString(): String {
        return "Album(title='$title', artist='$artist', imageBitmap=$imageBitmap, tracks=$tracks)"
    }


}

