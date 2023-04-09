package fhnw.emoba.freezerapp.ui.Tabs

import RadioTab
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.AvailableTabs


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            topBar = { Bar(model) },
            content = { padding ->
                Body(model, padding)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Bar(model: FreezerModel) {
    with(model) {
        TopAppBar(title = { Text(title) })
    }
}

@Composable
private fun Body(model: FreezerModel, padding: PaddingValues) {
    with(model) {
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab.ordinal) {
                AvailableTabs.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tab.title) }
                    )
                }
            }
            when (selectedTab) {
                AvailableTabs.HITS -> HitsTab(model)
                AvailableTabs.SONGS -> SongsTab(model)
                AvailableTabs.ALBUMS -> AlbumsTab(model)
                AvailableTabs.RADIO -> RadioTab(model)
            }
        }
    }
}