package fhnw.emoba.freezerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fhnw.emoba.freezerapp.model.FreezerModel


@Composable
fun AppUI(model: FreezerModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { println("Menu clicked!") },
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Open navigation menu"
            )
        }
        Text(
            text = "FreezerApp",
            modifier = Modifier.weight(1f),
            style = TextStyle(fontSize = 20.sp),
            textAlign = TextAlign.Left
        )
    }
}

@Preview
@Composable
fun Preview(){
    AppUI(FreezerModel)
}