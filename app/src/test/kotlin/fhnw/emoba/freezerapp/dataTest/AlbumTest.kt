package fhnw.emoba.freezerapp.dataTest

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Song
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class AlbumTest {

    @Test
    fun `test creating an album with an image bitmap`() {
        val title = "My Album"
        val artist = "My Artist"

        // Create a mock Bitmap object
        val mockBitmap = mockk<Bitmap>()
        every { mockBitmap.width } returns 100
        every { mockBitmap.height } returns 100

        // Call the asImageBitmap() function on the mock Bitmap
        val imageBitmap = mockBitmap.asImageBitmap()

        // Create the album with the image bitmap
        val album = Album(title, artist, imageBitmap)

        // Assert that the album has the correct properties
        assertEquals(title, album.title)
        assertEquals(artist, album.artist)
        assertEquals(imageBitmap, album.imageBitmap)
    }

    @Test
    fun `test creating an album with default parameters`() {
        val title = "My Album"
        val artist = "My Artist"

        // Create the album with default parameters
        val album = Album(title, artist)

        // Assert that the album has the correct properties
        assertEquals(title, album.title)
        assertEquals(artist, album.artist)
        assertTrue(album.tracks.isEmpty())
        assertNull(album.imageBitmap)
    }

    @Test
    fun `test adding tracks to an album`() {
        val title = "My Album"
        val artist = "My Artist"

        // Create an empty list of tracks
        val tracks = emptyList<Song>()

        // Create the album with the empty list of tracks
        val album = Album(title, artist, tracks = tracks)

        // Add a new track to the album
        val newTrack = Song("New Track", "New Artist", "https://newtrack.com", null, false)
        album.tracks = album.tracks + newTrack

        // Assert that the album has the correct number of tracks
        assertEquals(1, album.tracks.size)
        assertEquals(newTrack, album.tracks[0])
    }

    @Test
    fun `test converting an album to a string`() {
        val title = "My Album"
        val artist = "My Artist"

        // Create the album with default parameters
        val album = Album(title, artist)

        // Assert that the album's string representation is correct
        assertEquals("Album(title='My Album', artist='My Artist', imageBitmap=null, tracks=[])", album.toString())
    }

}