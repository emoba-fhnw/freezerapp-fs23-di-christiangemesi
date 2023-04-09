import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.data.Song
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object MovieService {

    fun filterSongBySearch(searchQuery: String) : List<Song> {
        val url = URL("https://api.deezer.com/search/track?q=$searchQuery")
        val connection = url.openConnection() as HttpsURLConnection

        return try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()

            val jsonObject = JSONObject(jsonString)
            val songsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val songs = mutableListOf<Song>()

            for (i in 0 until songsJsonArray.length()) {
                val songJson = songsJsonArray.getJSONObject(i)
                val title = songJson.getString("title")
                val artist = songJson.getJSONObject("artist").getString("name")
                val preview = songJson.getString("preview")
                val image = songJson.getJSONObject("album").getString("cover_medium")
                val imageBitmap = downloadImage(image)

                songs.add(Song(title, artist, preview, imageBitmap, false))
            }

            songs
        } finally {
            connection.disconnect()
        }
    }


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

                    filteredRadio.add(Radio(title, false, imageBitmap, tracks))

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
            BitmapFactory.decodeStream(connection.inputStream).asImageBitmap()
        } catch (e: Exception) {
            null
        }
    }

    private fun downloadTracks(tracklist: String): List<String> {
        val url = URL(tracklist)
        val connection = url.openConnection() as HttpsURLConnection

        return try {
            connection.connect()
            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            val tracksJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val tracks = mutableListOf<String>()

            for (i in 0 until tracksJsonArray.length()) {
                val trackJson = tracksJsonArray.getJSONObject(i)
                val previewUrl = trackJson.getString("preview")
                tracks.add(previewUrl)
            }

            tracks
        } finally {
            connection.disconnect()
        }
    }
}
