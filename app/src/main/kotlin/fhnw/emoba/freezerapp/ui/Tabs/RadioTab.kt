package fhnw.emoba.freezerapp.ui.Tabs

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.model.FreezerModel
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun RadioTab(model: FreezerModel) {

    Column {
        val radioList = model.movieList
        radioList.forEach { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item.imageBitmap?.let { // Use the downloaded image if available
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
                Text(item.title)
            }
        }
    }

}




