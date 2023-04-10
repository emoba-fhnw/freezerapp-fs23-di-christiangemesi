package fhnw.emoba.freezerapp.model

import MovieService
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.data.Album
import fhnw.emoba.freezerapp.data.Radio
import fhnw.emoba.freezerapp.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FreezerModel(private val service: MovieService) {
    val title = "Tabs Example App"
    var selectedTab by mutableStateOf(AvailableTabs.HITS)

    var loading by mutableStateOf(false)
    var radioList by mutableStateOf(emptyList<Radio>())
    var favoriteRadios by mutableStateOf(emptyList<Radio>())

    var songsList by mutableStateOf(emptyList<Song>())
    var favoriteSongs by mutableStateOf(emptyList<Song>())

    var albumsList by mutableStateOf(emptyList<Album>())

    private val background = SupervisorJob()
    private val modelScope = CoroutineScope(background + Dispatchers.IO)

    private val player = MediaPlayer().apply {
        setOnCompletionListener { playerIsReady = true }
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        setOnPreparedListener {
            start()
        }
    }

    var playerIsReady by mutableStateOf(true)
        private set

    var currentRadio by mutableStateOf<Radio?>(null) // track currently playing radio
        private set

    var currentSong by mutableStateOf<Song?>(null) // track currently playing song
        private set

    fun startPlayer(song: Song) {
        playerIsReady = false
        if (currentSong == song && !player.isPlaying) {
            player.start()
        } else {
            currentSong = song
            currentRadio = null
            player.reset()
            player.setDataSource(song.preview)
            player.prepareAsync()
        }
    }

    fun startPlayer(randomTrack: String) {
        playerIsReady = false
        if (currentRadio != null && currentRadio!!.tracks.contains(randomTrack) && !player.isPlaying) {
            player.start()
        } else {
            currentRadio = radioList.find { it.tracks.contains(randomTrack) }
            currentRadio?.let {
                pausePlayer()
            }
            player.reset()
            player.setDataSource(randomTrack)
            player.prepareAsync()
        }
    }

    fun pausePlayer() {
        player.pause()
        playerIsReady = true
        currentRadio = null
        currentSong = null
    }

    fun fromStart() {
        player.seekTo(0)
        player.start()
        playerIsReady = false
    }

    fun loadRadioStationsAsync() {
        loading = true
        modelScope.launch {
            radioList = service.getRadioStations()
            loading = false
        }
    }

    fun loadSongAsync(songQuery: String) {
        modelScope.launch {
            songsList = service.filterSongBySearch(songQuery)
            println("Songslist from backend: " + songsList.size)
        }
    }

    fun loadAlbumAsync(albumQuery: String) {
        modelScope.launch {
            albumsList = service.filterAlbumsBySearch(albumQuery)
            println("Albumslist from backend: " + albumsList.size)
        }
    }

    fun toggleFavorite(radio: Radio) {
        radio.isFavorite
        radio.isFavorite = !radio.isFavorite
        favoriteRadios = if (radio.isFavorite) {
            favoriteRadios + radio
        } else {
            favoriteRadios - radio
        }
    }

    fun toggleFavorite(song: Song) {
        song.isFavorite = !song.isFavorite
        favoriteSongs = if (song.isFavorite) {
            favoriteSongs + song
        } else {
            favoriteSongs - song
        }
    }
}