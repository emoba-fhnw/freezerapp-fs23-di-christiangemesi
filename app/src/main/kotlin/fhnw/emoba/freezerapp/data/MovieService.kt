import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Radio
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object MovieService {


    fun getRadioStations(): List<Radio> {
        val url = URL("https://api.deezer.com/radio")
        val connection = url.openConnection() as HttpsURLConnection

        try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()

            val JSONObject = JSONObject(jsonString)
            if (!JSONObject.has("data")) {
                return emptyList()
            }

            val radioJsonArray = JSONObject(jsonString).getJSONArray("data")

            val filteredRadio = mutableListOf<Radio>()

            for (i in 0 until radioJsonArray.length()) {
                val radioJson = radioJsonArray.getJSONObject(i)
                val title = radioJson.getString("title")
                val cover = radioJson.getString("picture_medium")
                val tracklist = radioJson.getString("tracklist")

                // Download the image
                val imageUrl = URL(cover)
                val connection = imageUrl.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input = connection.inputStream
                val imageBitmap = BitmapFactory.decodeStream(input).asImageBitmap()

                filteredRadio.add(Radio(title, cover, tracklist, false, imageBitmap))
            }

            return filteredRadio

        } finally {
            connection.disconnect()
        }
    }
}
