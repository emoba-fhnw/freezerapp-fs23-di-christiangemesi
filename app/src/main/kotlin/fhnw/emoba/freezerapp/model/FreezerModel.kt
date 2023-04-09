package fhnw.emoba.freezerapp.model

import MovieService
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var movieList by mutableStateOf<List<Radio>>(emptyList())
    var favoriteRadios by mutableStateOf<List<Radio>>(emptyList())

    var songsList by mutableStateOf<List<Song>>(emptyList())

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

    var currentRadio: Radio? by mutableStateOf(null) // track currently playing radio

    fun startPlayer(randomTrack: String) {
        playerIsReady = false
        try {
            if (currentRadio != null && currentRadio!!.tracks.contains(randomTrack) && !player.isPlaying) { // if the same track is already playing, resume playing
                player.start()
            } else {
                currentRadio?.let { // stop playing the current radio
                    pausePlayer()
                }
                currentRadio = null
                for (radio in movieList) { // find the radio with the selected track and set it as the current radio
                    if (radio.tracks.contains(randomTrack)) {
                        currentRadio = radio
                        break
                    }
                }
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
        currentRadio = null // reset current radio
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

    fun loadSongAsync(songQuery: String) {
        modelScope.launch {
            songsList = service.filterSongBySearch(songQuery)
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