package fhnw.emoba.freezerapp.ui.Tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun RadioTab(model: FreezerModel) {

    Column {
        Text("Radioooooo")
        val movie = model.movieList
        Text(movie.toString())
    }
}