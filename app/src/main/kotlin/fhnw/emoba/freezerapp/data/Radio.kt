package fhnw.emoba.freezerapp.data

import org.json.JSONObject

class Radio(
    val title: String,
    val picture: String,
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
        return "RadioStation(title='$title', picture='$picture', tracklist='$tracklist', isFavorite=$isFavorite)"
    }
}