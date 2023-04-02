package fhnw.emoba.freezerapp.ui


import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import fhnw.emoba.freezerapp.model.FreezerModel


@Composable
fun AppUI(model: FreezerModel) {
    BackHandler(enabled = true) {

    }
}
