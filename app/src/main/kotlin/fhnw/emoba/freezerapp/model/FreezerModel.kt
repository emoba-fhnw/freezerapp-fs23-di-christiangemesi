package fhnw.emoba.freezerapp.model

import MovieService
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.data.Radio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class FreezerModel(private val service: MovieService) {
    val title = "Tabs Example App"

    var selectedTab by mutableStateOf(AvailableTabs.HITS)

    var loading by mutableStateOf(false)
    var movieList: List<Radio> by mutableStateOf(emptyList())
    var favoriteRadios: List<Radio> by mutableStateOf(emptyList())

    private val background = SupervisorJob()
    private val modelScope = CoroutineScope(background + Dispatchers.IO)

    var playerIsReady by mutableStateOf(true)
    private var currentlyPlaying = ""  // wird nur intern gebraucht, soll kein Recompose ausloesen, daher auch kein MutableState

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

    fun startPlayer(randomTrack: String){
        playerIsReady = false
        try {
            if (currentlyPlaying == randomTrack && !player.isPlaying) {
                player.start()
            } else {
                currentlyPlaying = randomTrack
                player.reset()
                player.setDataSource(randomTrack)
                player.prepareAsync()
            }
        } catch (e: Exception) {
            playerIsReady = true
        }
    }

    fun pausePlayer() {
        player.pause()
        playerIsReady = true
    }

    fun fromStart() {
        player.seekTo(0)
        player.start()
        playerIsReady = false
    }

    fun loadMovieAsync() {
        loading = true
        movieList = emptyList()
        modelScope.launch {
            movieList = service.getRadioStations()
            loading = false
        }
    }

    fun toggleFavorite(radio: Radio) {
        radio.isFavorite = !radio.isFavorite
        if (radio.isFavorite) {
            favoriteRadios = favoriteRadios + Radio(radio.title, radio.image, radio.tracklist, radio.isFavorite, radio.imageBitmap)
        } else {
            favoriteRadios = favoriteRadios - radio
        }
    }


}
