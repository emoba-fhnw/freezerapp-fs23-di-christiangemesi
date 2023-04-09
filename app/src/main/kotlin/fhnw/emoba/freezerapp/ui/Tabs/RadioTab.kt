import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun RadioTab(model: FreezerModel) {
    val radioList = model.movieList

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(radioList) { item ->
            RadioItem(item)
        }
    }
}

@Composable
private fun RadioItem(radio: Radio) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        radio.imageBitmap?.let {
            Image(
                it,
                contentDescription = "Radio Image",
                Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.width(16.dp))
        Text(radio.title)
    }
}
