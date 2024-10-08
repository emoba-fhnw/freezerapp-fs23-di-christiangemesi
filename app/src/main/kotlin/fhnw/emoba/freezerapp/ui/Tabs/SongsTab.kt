import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongsTab(model: FreezerModel) {
    var searchText by remember { mutableStateOf("") }
    var songs by remember { mutableStateOf(emptyList<Song>()) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search") },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                // Call API to search songs
                model.loadSongAsync(searchText)
            }
        ) {
            Text("Search")
        }
    }

    LaunchedEffect(model.songsList) {
        songs = model.songsList
    }

    // Display the search results
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        songs.forEach { song ->
            item {
                SongItem(song, model, songs) // pass the model instance
            }
        }
    }
}


@Composable
fun SongItem(song: Song, model: FreezerModel, songs: List<Song>) {
    var isFavorite by remember { mutableStateOf(song.isFavorite) }
    var isPlaying by remember { mutableStateOf(model.currentSong == song && !model.playerIsReady) } // track playing status

    // Update isPlaying state if current song changes
    LaunchedEffect(model.currentSong) {
        isPlaying = model.currentSong == song && !model.playerIsReady
    }

    val songIndex = songs.indexOf(song)

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        song.imageBitmap?.let { bitmap ->
            Image(
                bitmap,
                contentDescription = "Album Image",
                Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(song.title)
            Spacer(Modifier.height(4.dp))
            Text(song.artist)
        }
        IconButton(onClick = {
            if (isPlaying) {
                model.pausePlayer()
            } else {
                model.startPlayer(song)
            }
        }) {
            Icon(
                if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                contentDescription = if (isPlaying) "Pause Song" else "Play Song"
            )
        }
        IconButton(onClick = {
            isFavorite = !isFavorite
            model.toggleFavorite(song)
        }) {
            Icon(
                if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
            )
        }
        IconButton(onClick = {
            model.fromStart(song)
        }) {
            Icon(
                Icons.Filled.Replay,
                contentDescription = "Play from start"
            )
        }
        IconButton(onClick = {
            val nextSongIndex = songIndex + 1
            if (nextSongIndex < songs.size) {
                model.startPlayer(songs[nextSongIndex])
            } else {
                model.startPlayer(songs.first())
            }
        }) {
            Icon(
                Icons.Filled.SkipNext,
                contentDescription = "Next song"
            )
        }

    }
}
