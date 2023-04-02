package fhnw.emoba.freezerapp.ui


import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.tabs.AvailableTabs
import fhnw.emoba.freezerapp.ui.Tabs.HitsTab


@Composable
fun AppUI(model: FreezerModel) {
    BackHandler(enabled = true) {
        model.selectedTab = AvailableTabs.HITS
    }
    when (model.selectedTab) {
        AvailableTabs.HITS -> HitsTab(model)
        AvailableTabs.SONGS -> TODO()
        AvailableTabs.ALBUMS -> TODO()
        AvailableTabs.RADIO -> TODO()
    }



}
