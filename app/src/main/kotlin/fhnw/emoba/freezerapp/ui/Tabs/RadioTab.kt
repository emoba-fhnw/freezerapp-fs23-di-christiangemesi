import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import androidx.compose.runtime.*
import fhnw.emoba.freezerapp.ui.Tabs.AlbumItem


@Composable
fun RadioTab(model: FreezerModel) {


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
    }

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            model.radioList.forEach { album ->
                item {
                    AlbumItem(album, model) // pass the model instance
                }
            }
        }
    }
}
