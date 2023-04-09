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
        Text("Radioooooo")
        val radioList = model.movieList
        radioList.forEach { item ->
            Text(item.title)
            item.image?.let { ShowImage(it) }

        }
    }

}

@Composable
fun ShowImage(it: String) {
    val url = URL(it)
    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val input = connection.inputStream
    val myBitmap = BitmapFactory.decodeStream(input)
    Image(
        bitmap = myBitmap.asImageBitmap(),
        contentDescription = "Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
    )
}


