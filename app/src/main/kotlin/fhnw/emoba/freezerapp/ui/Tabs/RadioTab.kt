package fhnw.emoba.freezerapp.ui.Tabs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun RadioTab(model: FreezerModel) {

    Column {
        Text("Radioooooo")
        val radioList = model.movieList
        radioList.forEach { radio ->
            Text(radio.title)
        }
    }


}

@Composable
fun RadioItem(radio: Radio) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column {
            Text(text = radio.title)
            // add more text components to display other song details
        }
    }
}