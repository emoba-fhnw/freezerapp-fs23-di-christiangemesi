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

class FreezerModel(private val service: MovieService) {
    val title = "Tabs Example App"

    var selectedTab by mutableStateOf(AvailableTabs.HITS)

    var loading by mutableStateOf(false)
    var movieList by mutableStateOf<List<Radio>>(emptyList())
    var favoriteRadios by mutableStateOf<List<Radio>>(emptyList())

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

    private var currentTrack: String? = null
    var currentRadio by mutableStateOf<Radio?>(null)


    fun startPlayer(randomTrack: String) {
        playerIsReady = false
        try {
            if (currentTrack == randomTrack && !player.isPlaying) {
                player.start()
            } else {
                currentTrack = randomTrack
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
        currentRadio = null // set current radio to null
    }


    fun fromStart() {
        player.seekTo(0)
        player.start()
        playerIsReady = false
    }

    fun loadMovieAsync() {
        loading = true
        modelScope.launch {
            movieList = service.getRadioStations()
            loading = false
        }
    }

    fun toggleFavorite(radio: Radio) {
        radio.isFavorite = !radio.isFavorite
        favoriteRadios = if (radio.isFavorite) {
            favoriteRadios + radio
        } else {
            favoriteRadios - radio
        }
    }
}
