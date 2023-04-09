package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap
import org.json.JSONObject

class Radio(
    val title: String,
    val image: String,
    val tracklist: String,
    var isFavorite: Boolean
) {

    constructor(jsonObject: JSONObject) : this(
        jsonObject.getString("title"),
        jsonObject.getString("picture"),
        jsonObject.getString("tracklist"),
        false
    )

    override fun toString(): String {
        return "RadioStation(title='$title', picture='$image', tracklist='$tracklist', isFavorite=$isFavorite)"
    }
}