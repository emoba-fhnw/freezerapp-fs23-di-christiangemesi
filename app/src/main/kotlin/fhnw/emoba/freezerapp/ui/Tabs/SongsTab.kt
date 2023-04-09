import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
    var currentIndex by remember { mutableStateOf(0) }

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

    // Display the search results
    Column {
        songs = model.songsList // update the list of songs when it changes
        songs.forEachIndexed { index, song ->
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    model.fromStart()
                }) {
                    Icon(
                        Icons.Filled.Replay,
                        contentDescription = "Restart Song"
                    )
                }
                IconButton(onClick = {
                    if (model.playerIsReady && model.currentSong == song) { // if playing and current song is selected, pause
                        model.pausePlayer()
                    } else { // if not playing or different song is selected, start playing
                        model.startPlayer(song)
                    }
                }) {
                    Icon(
                        if (model.playerIsReady && model.currentSong == song) Icons.Filled.Pause else Icons.Filled.PlayArrow, // change icon based on playing status and current song
                        contentDescription = if (model.playerIsReady && model.currentSong == song) "Pause Song" else "Play Song"
                    )
                }
                IconButton(onClick = {
                    currentIndex = (index + 1) % songs.size
                    model.startPlayer(songs[currentIndex])
                }) {
                    Icon(
                        Icons.Filled.SkipNext,
                        contentDescription = "Next Song"
                    )
                }
                IconButton(onClick = {
                    song.isFavorite = !song.isFavorite
                    model.toggleFavorite(song)
                }) {
                    Icon(
                        if (song.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (song.isFavorite) "Remove from favorites" else "Add to favorites"
                    )
                }
            }
        }
    }
}


