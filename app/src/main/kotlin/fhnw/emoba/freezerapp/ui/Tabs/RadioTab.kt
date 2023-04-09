import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.model.FreezerModel
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.runtime.*

@Composable
fun RadioTab(model: FreezerModel) {
    val radioList = model.movieList

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        radioList.forEach { radio ->
            item {
                RadioItem(radio, model) // pass the model instance
            }
        }
    }
}


@Composable
fun RadioItem(radio: Radio, model: FreezerModel) {
    var isFavorite by remember { mutableStateOf(radio.isFavorite) }
    var isPlaying by remember { mutableStateOf(false) } // track playing status
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        radio.imageBitmap?.let { bitmap ->
            Image(
                bitmap,
                contentDescription = "Radio Image",
                Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(radio.title)
            Spacer(Modifier.height(4.dp))
            Row {
                IconButton(onClick = {
                    isFavorite = !isFavorite
                    model.toggleFavorite(radio)
                }) {
                    Icon(
                        if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
                    )
                }
                IconButton(onClick = {
                    if (model.currentRadio == null || model.currentRadio == radio) { // no radio is currently playing or this radio is already playing
                        val randomTrack = radio.tracks.random()
                        model.startPlayer(randomTrack)
                        model.currentRadio = radio // set current radio to this radio
                        isPlaying = true // set playing status to true
                    } else { // another radio is playing, stop it and start playing this radio
                        model.pausePlayer()
                        val randomTrack = radio.tracks.random()
                        model.startPlayer(randomTrack)
                        model.currentRadio = radio // set current radio to this radio
                        isPlaying = true // set playing status to true
                    }
                }) {
                    Icon(
                        if (isPlaying && model.currentRadio == radio) Icons.Filled.Pause else Icons.Filled.PlayArrow, // change icon based on playing status and current radio
                        contentDescription = if (isPlaying && model.currentRadio == radio) "Pause Radio" else "Play Radio"
                    )
                }
            }
        }
    }
}
