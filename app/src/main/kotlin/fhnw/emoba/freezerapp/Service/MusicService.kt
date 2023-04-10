import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Song
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


/**
 * A simple HTTP service that can be used to make HTTP requests. Own implementation because the
 * the other class is final and cannot be mocked.
 */
class HttpService {
    fun get(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        return try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()
            jsonString
        } finally {
            connection.disconnect()
        }
    }
}

object MovieService {

    private const val API_DEEZER_BASE_URL = "https://api.deezer.com"

    private val httpService = HttpService()

    fun getAllRadioStations(): List<Album> {
        val url = "$API_DEEZER_BASE_URL/radio"
        val jsonString = httpService.get(url)

        return try {
            val jsonObject = JSONObject(jsonString)
            val albumsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val filteredAlbums = mutableListOf<Album>()

            for (i in 0 until albumsJsonArray.length()) {
                val albumJson = albumsJsonArray.getJSONObject(i)
                val title = albumJson.getString("title")
                val cover = albumJson.getString("picture_medium")
                val imageBitmap = downloadImageBitmap(cover)

                val tracklist = albumJson.getString("tracklist")
                val songs = downloadSongs(tracklist, imageBitmap)

                filteredAlbums.add(Album(title, "", imageBitmap, songs))

            }
            println("Albums: ${filteredAlbums.toString()}")
            filteredAlbums
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAlbumsBySearch(searchQuery: String): List<Album> {
        val url = "$API_DEEZER_BASE_URL/search/album?q=$searchQuery"
        val jsonString = httpService.get(url)

        return try {
            val jsonObject = JSONObject(jsonString)
            val albumsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val filteredAlbums = mutableListOf<Album>()

            for (i in 0 until albumsJsonArray.length()) {
                val albumJson = albumsJsonArray.getJSONObject(i)
                val title = albumJson.getString("title")
                val artist = albumJson.getJSONObject("artist").getString("name")
                val coverUrl = albumJson.getString("cover_medium")
                val imageBitmap = downloadImageBitmap(coverUrl)
                val tracklistUrl = albumJson.getString("tracklist")
                val songs = downloadSongs(tracklistUrl, imageBitmap)

                filteredAlbums.add(Album(title, artist, imageBitmap, songs))
            }

            filteredAlbums
        } catch (e: Exception) {
            emptyList()
        }
    }


    fun getSongBySearch(searchQuery: String): List<Song> {
        val url = "$API_DEEZER_BASE_URL/search/track?q=$searchQuery"
        val jsonString = httpService.get(url)

        return try {
            val jsonObject = JSONObject(jsonString)
            val songsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val songs = mutableListOf<Song>()

            for (i in 0 until songsJsonArray.length()) {
                val songJson = songsJsonArray.getJSONObject(i)
                val title = songJson.getString("title")
                val artist = songJson.getJSONObject("artist").getString("name")
                val previewUrl = songJson.getString("preview")
                val coverUrl = songJson.getJSONObject("album").getString("cover_medium")
                val imageBitmap = downloadImageBitmap(coverUrl)
                songs.add(Song(title, artist, previewUrl, imageBitmap, false))
            }

            songs
        } catch (e: Exception) {
            emptyList()
        }
    }


    private fun downloadImageBitmap(urlString: String): ImageBitmap? {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.connect()
            BitmapFactory.decodeStream(connection.inputStream).asImageBitmap()
        } catch (e: Exception) {
            null
        } finally {
            connection.disconnect()
        }
    }

    private fun downloadSongs(tracklistUrl: String, cover: ImageBitmap?): List<Song> {
        val httpService = HttpService()

        val jsonString = httpService.get(tracklistUrl)
        val jsonObject = JSONObject(jsonString)

        val songsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

        val songs = mutableListOf<Song>()

        for (i in 0 until songsJsonArray.length()) {
            val songJson = songsJsonArray.getJSONObject(i)
            val previewUrl = songJson.getString("preview")
            val title = songJson.getString("title")

            songs.add(Song(title, "", previewUrl, cover, false))
        }

        return songs
    }

}
