package fhnw.emoba.freezerapp.MusicServiceTest

import HttpService
import MovieService
import io.mockk.coEvery
import io.mockk.mockk
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ServiceTest {

    private val httpService = mockk<HttpService>()

    @Test
    fun testGetAllRadioStations() {
        // Given
        val albumJsonArray = JSONArray().apply {
            put(JSONObject().apply {})
        }
        coEvery { httpService.get("https://api.deezer.com/radio") } returns JSONObject().apply {
            put("data", albumJsonArray)
        }.toString()

        // When
        val radioStations = MovieService.getAllRadioStations()

        // Then
        assertNotNull(radioStations)
        assertEquals(radioStations.size, 138)
        assertEquals(radioStations[0].title, "Hits")
        assertEquals(radioStations[1].title, "Indie")
    }

    @Test
    fun testGetSongBySearch() {
        // Given
        val songJsonArray = JSONArray().apply {}
        coEvery { httpService.get("https://api.deezer.com/search/track?q=query") } returns JSONObject().apply {
            put("data", songJsonArray)
        }.toString()

        // When
        val songs = MovieService.getSongBySearch("query")

        // Then
        assertNotNull(songs)
        assertEquals(songs.size, 25)
        assertEquals(songs[0].title, "Query")
        assertEquals(songs[0].artist, "DEN!Z")
        assertEquals(songs[0].preview, "https://cdns-preview-9.dzcdn.net/stream/c-906e39b8a097ab7a9be5d8993cd944c2-2.mp3")
        assertEquals(songs[0].isFavorite, false)
    }


}
