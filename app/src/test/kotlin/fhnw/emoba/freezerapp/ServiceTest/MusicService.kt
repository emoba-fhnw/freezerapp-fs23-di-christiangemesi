package fhnw.emoba.freezerapp.ServiceTest

import androidx.compose.ui.graphics.ImageBitmap
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Song
import io.mockk.*
import io.reactivex.Flowable.just
import junit.framework.TestCase.assertEquals
import org.json.JSONObject
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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

class MusicService {

    @Test
    fun `test getAllRadioStations() returns a list of albums`() {
        val mockAlbumJson = JSONObject(
            """
            {
                "data": [
                    {
                        "title": "Album 1",
                        "picture_medium": "https://example.com/album1.jpg",
                        "tracklist": "https://example.com/album1/tracks"
                    },
                    {
                        "title": "Album 2",
                        "picture_medium": "https://example.com/album2.jpg",
                        "tracklist": "https://example.com/album2/tracks"
                    }
                ]
            }
        """.trimIndent()
        )

        val mockConnection = mockk<HttpsURLConnection>()
        every { mockConnection.connect() } just Runs
        every { mockConnection.inputStream } returns mockk {
            every { bufferedReader().use { it.readText() } } returns mockAlbumJson.toString()
            just(runs)
        }
        every { mockConnection.disconnect() } just Runs

        val mockAlbum1Bitmap = mockk<ImageBitmap>()
        val mockAlbum2Bitmap = mockk<ImageBitmap>()

        every { MovieService.downloadImageBitmap("https://example.com/album1.jpg") } returns mockAlbum1Bitmap
        every { MovieService.downloadImageBitmap("https://example.com/album2.jpg") } returns mockAlbum2Bitmap

        val mockAlbum1Tracks = listOf(mockk<Song>())
        val mockAlbum2Tracks = listOf(mockk<Song>())

        every { MovieService.downloadSongs("https://example.com/album1/tracks", mockAlbum1Bitmap) } returns mockAlbum1Tracks
        every { MovieService.downloadSongs("https://example.com/album2/tracks", mockAlbum2Bitmap) } returns mockAlbum2Tracks

        val expectedAlbums = listOf(
            Album("Album 1", "", mockAlbum1Bitmap, mockAlbum1Tracks),
            Album("Album 2", "", mockAlbum2Bitmap, mockAlbum2Tracks)
        )

        val actualAlbums = MovieService.getAllRadioStations()

        assertEquals(expectedAlbums, actualAlbums)
    }

}