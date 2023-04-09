import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Radio
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object MovieService {

    fun getRadioStations(): List<Radio> {
        val url = URL("https://api.deezer.com/radio")
        val connection = url.openConnection() as HttpsURLConnection

        return try {
            connection.connect()
            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            if (!jsonObject.has("data")) {
                emptyList()
            } else {
                val radioJsonArray = jsonObject.getJSONArray("data")
                val filteredRadio = mutableListOf<Radio>()

                for (i in 0 until radioJsonArray.length()) {
                    val radioJson = radioJsonArray.getJSONObject(i)
                    val title = radioJson.getString("title")
                    val cover = radioJson.getString("picture_medium")
                    val imageBitmap = downloadImage(cover)
                    val tracklist = radioJson.getString("tracklist")
                    val tracks = downloadTracks(tracklist)

                    filteredRadio.add(Radio(title, cover, tracklist, false, imageBitmap, tracks))
                }

                filteredRadio
            }
        } finally {
            connection.disconnect()
        }
    }

    private fun downloadImage(urlString: String): ImageBitmap? {
        return try {
            val imageUrl = URL(urlString)
            val connection = imageUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input).asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    private fun downloadTracks(tracklist: String): List<String> {
        val url = URL(tracklist)
        val connection = url.openConnection() as HttpsURLConnection

        try {
            connection.connect()
            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            if (jsonObject.has("data")) {
                val tracksJsonArray = jsonObject.getJSONArray("data")
                val tracks = mutableListOf<String>()

                for (i in 0 until tracksJsonArray.length()) {
                    val trackJson = tracksJsonArray.getJSONObject(i)
                    val previewUrl = trackJson.getString("preview")
                    tracks.add(previewUrl)
                }
                return tracks;
            } else {
                return emptyList()
            }
        } finally {
            connection.disconnect()
        }
    }



}
