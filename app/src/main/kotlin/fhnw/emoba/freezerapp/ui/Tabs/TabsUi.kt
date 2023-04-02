package fhnw.emoba.freezerapp.ui.Tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.model.tabs.AbailableTabs


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsUI(model: FreezerModel) {
    MaterialTheme {
        Scaffold(
            topBar = { Bar(model) },
            floatingActionButton = { FAB(model) },
            floatingActionButtonPosition = FabPosition.End,
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
        //TODO: mit TabsRow und Tab ersetzen
        Text("to be replaced")
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab.ordinal) {
                AbailableTabs.values().forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = { Text(tab.title) }
                    )
                }
            }
            ContentBox(tabContent = selectedTab)
        }
    }

}

@Composable
private fun FAB(model: FreezerModel) {
    with(model) {
        FloatingActionButton(
            onClick = { selectedTab = AbailableTabs.FIRST }) {
            Icon(Icons.Filled.Home, "Spring")
        }
    }
}

@Composable
private fun ContentBox(tabContent: AbailableTabs) {
    Text(tabContent.title)
}