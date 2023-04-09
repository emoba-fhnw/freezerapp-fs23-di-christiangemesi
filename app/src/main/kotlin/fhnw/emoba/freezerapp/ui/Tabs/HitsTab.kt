import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel

@Composable
fun HitsTab(model: FreezerModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        model.favoriteRadios.forEach { radio ->
            item {
                RadioItem(radio, model) // pass the model instance
            }
        }

    }
}
