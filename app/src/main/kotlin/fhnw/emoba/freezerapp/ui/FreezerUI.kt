package fhnw.emoba.freezerapp.ui

import HitsTab
import RadioTab
import SongsTab
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fhnw.emoba.R
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.AvailableTabs
import fhnw.emoba.freezerapp.ui.Tabs.AlbumsTab


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
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    val deezerLogo: Painter = painterResource(id = R.drawable.deezerlogo)
                    Spacer(modifier =Modifier.width(100.dp))
                    Image(
                        painter = deezerLogo,
                        contentDescription = "Deezer logo",
                        modifier = Modifier
                            .size(24.dp)
                            .scale(10f)
                    )
                }
            }
        )
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