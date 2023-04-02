package fhnw.emoba.freezerapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.model.tabs.AbailableTabs


object FreezerModel {
    val title = "Tabs Example App"

    var selectedTab by mutableStateOf(AbailableTabs.FIRST)
}