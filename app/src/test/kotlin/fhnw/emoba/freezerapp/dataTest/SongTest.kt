package fhnw.emoba.freezerapp.dataTest

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.Song
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.*
import org.junit.Test


class SongTest {

    @Test
    fun `test creating a song with an image bitmap`() {
        val title = "My Song"
        val artist = "My Artist"
        val preview = "https://mysong.com"

        // Create a mock Bitmap object
        val mockBitmap = mockk<Bitmap>()
        every { mockBitmap.width } returns 100
        every { mockBitmap.height } returns 100

        // Call the asImageBitmap() function on the mock Bitmap
        val imageBitmap = mockBitmap.asImageBitmap()

        // Create the song with the image bitmap
        val song = Song(title, artist, preview, imageBitmap, false)

        // Assert that the song has the correct properties
        assertEquals(title, song.title)
        assertEquals(artist, song.artist)
        assertEquals(preview, song.preview)
        assertEquals(imageBitmap, song.imageBitmap)
        assertFalse(song.isFavorite)
    }

    @Test
    fun `test creating a song with default parameters`() {
        val title = "My Song"
        val artist = "My Artist"
        val preview = "https://mysong.com"
        val imageBitmap = null
        val isFavorite = false

        // Create the song with default parameters
        val song = Song(title, artist, preview,imageBitmap, isFavorite)

        // Assert that the song has the correct properties
        assertEquals(title, song.title)
        assertEquals(artist, song.artist)
        assertEquals(preview, song.preview)
        assertNull(song.imageBitmap)
        assertFalse(song.isFavorite)
    }

    @Test
    fun `test converting a song to a string`() {
        val title = "My Song"
        val artist = "My Artist"
        val preview = "https://mysong.com"
        val imageBitmap = null
        val isFavorite = false

        // Create the song with default parameters
        val song = Song(title, artist, preview,imageBitmap, isFavorite)

        // Assert that the song's string representation is correct
        assertEquals("Song(title='My Song', artist='My Artist', preview='https://mysong.com', imageBitmap=null, isFavorite=false)", song.toString())
    }

}