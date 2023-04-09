package fhnw.emoba.freezerapp.ui.Tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.freezerapp.model.FreezerModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsTab(model: FreezerModel) {
    Column {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )
    }
    Column {
        Text("Albumsssss")
    }
}