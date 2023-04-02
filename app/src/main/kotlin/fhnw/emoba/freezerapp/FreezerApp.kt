package fhnw.emoba.freezerapp

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import fhnw.emoba.EmobaApp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.Tabs.TabsUI


object FreezerApp : EmobaApp {

    override fun initialize(activity: ComponentActivity) {
    }

    @Composable
    override fun CreateUI() {
        TabsUI(FreezerModel)
    }

}