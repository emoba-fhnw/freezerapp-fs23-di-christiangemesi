package fhnw.emoba.freezerapp.ui.Tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import fhnw.emoba.freezerapp.data.Song
import fhnw.emoba.freezerapp.model.FreezerModel

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

    // Display the search results
    Column {
        songs = model.songsList // update the list of songs when it changes
        songs.forEach { song ->
            Text("${song.title} by ${song.artist}")
        }
    }
}
