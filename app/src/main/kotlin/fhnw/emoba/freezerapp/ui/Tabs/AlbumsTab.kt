package fhnw.emoba.freezerapp.ui.Tabs

import SongItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.model.FreezerModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsTab(model: FreezerModel) {
    var searchText by remember { mutableStateOf("") }
    var albums by remember { mutableStateOf(emptyList<Album>()) }

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
                model.loadAlbumAsync(searchText)
            }
        ) {
            Text("Search")
        }
    }

    LaunchedEffect(model.albumsList) {
        albums = model.albumsList
    }

    LaunchedEffect(searchText) {
        val newAlbums = model.albumsList
        albums = newAlbums
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            albums.forEach { album ->
                item {
                    AlbumItem(album, model) // pass the model instance
                }
            }
        }
    }
}


@Composable
fun AlbumItem(album: Album, model: FreezerModel) {
    var expanded by remember { mutableStateOf(false) }
    val songs = remember { album.tracks }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        album.imageBitmap?.let { bitmap ->
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
            Text(album.title)
            Spacer(Modifier.height(4.dp))
            Text(album.artist)
        }
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand"
            )
        }
    }
    if (expanded) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            songs.forEach { song ->
                SongItem(song, model, songs) // pass the album's tracks to SongItem
            }
        }
    }
}

