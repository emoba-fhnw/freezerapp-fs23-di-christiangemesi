package fhnw.emoba.freezerapp.model

import MovieService
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
    var movieList: List<Radio> by mutableStateOf(emptyList())

    private val background = SupervisorJob()
    private val modelScope = CoroutineScope(background + Dispatchers.IO)

    fun loadMovieAsync() {
        loading = true
        movieList = emptyList()
        modelScope.launch {
            movieList = service.getRadioStations()
            loading = false
        }
    }
}