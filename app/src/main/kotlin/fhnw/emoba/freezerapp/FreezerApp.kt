package fhnw.emoba.freezerapp

import MovieService
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.Tabs.AppUi


object FreezerApp : EmobaApp {
    private lateinit var model: FreezerModel

    override fun initialize(activity: ComponentActivity) {
        val service = MovieService
        model = FreezerModel(service)
        model.loadRadioStationsAsync()
    }

    @Composable
    override fun CreateUI() {
        AppUi(model)
    }

}