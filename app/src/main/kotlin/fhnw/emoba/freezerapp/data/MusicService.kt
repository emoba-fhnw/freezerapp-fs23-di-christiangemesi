import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.data.Song
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object MovieService {


    fun getAllRadioStations(): List<Album> {
        val url = URL("https://api.deezer.com/radio")
        val connection = url.openConnection() as HttpsURLConnection
        return try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()

            val jsonObject = JSONObject(jsonString)
            val albumsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val filteredAlbums = mutableListOf<Album>()

            for (i in 0 until albumsJsonArray.length()) {
                val albumJson = albumsJsonArray.getJSONObject(i)
                val title = albumJson.getString("title")
                val cover = albumJson.getString("picture_medium")
                val imageBitmap = downloadImage(cover)

                val tracklist = albumJson.getString("tracklist")
                val songs = downloadSongTitlesAndTracks(tracklist, imageBitmap)

                filteredAlbums.add(Album(title, "", imageBitmap, songs))

            }
            println("Albums: ${filteredAlbums.toString()}")
            filteredAlbums
        } finally {
            connection.disconnect()
        }
    }
    fun filterAlbumsBySearch(searchQuery: String): List<Album> {
        val url = URL("https://api.deezer.com/search/album?q=$searchQuery")
        val connection = url.openConnection() as HttpsURLConnection
        return try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()

            val jsonObject = JSONObject(jsonString)
            val albumsJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val filteredAlbums = mutableListOf<Album>()

            for (i in 0 until albumsJsonArray.length()) {
                val albumJson = albumsJsonArray.getJSONObject(i)
                val title = albumJson.getString("title")
                val artist = albumJson.getJSONObject("artist").getString("name")
                val cover = albumJson.getString("cover_medium")
                val tracklist = albumJson.getString("tracklist")
                val coverBitmap = downloadImage(cover)
                val song = downloadSongTitlesAndTracks(tracklist, coverBitmap)


                filteredAlbums.add(Album(title, artist, coverBitmap, song))
            }

            filteredAlbums
        } finally {
            connection.disconnect()
        }
    }


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

    private fun downloadSongTitlesAndTracks(tracklist: String, cover: ImageBitmap?  ): List<Song> {
        val url = URL(tracklist)
        val connection = url.openConnection() as HttpsURLConnection

        return try {
            connection.connect()
            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)

            val tracksJsonArray = jsonObject.optJSONArray("data") ?: return emptyList()

            val tracks = mutableListOf<Song>()

            for (i in 0 until tracksJsonArray.length()) {
                val trackJson = tracksJsonArray.getJSONObject(i)
                val previewUrl = trackJson.getString("preview")
                val title = trackJson.getString("title")

                tracks.add(Song(title, "", previewUrl, cover, false) )   ;
            }

            tracks
        } finally {
            connection.disconnect()
        }
    }
}
