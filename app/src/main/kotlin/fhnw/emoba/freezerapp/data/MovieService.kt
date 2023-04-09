import fhnw.emoba.freezerapp.data.Radio
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object MovieService {


    fun getRadioStations(): List<Radio> {
        val url = URL("https://api.deezer.com/radio")
        val connection = url.openConnection() as HttpsURLConnection

        try {
            connection.connect()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val jsonString = reader.readText()
            reader.close()

            val JSONObject = JSONObject(jsonString)
            if (!JSONObject.has("data")) {
                return emptyList()
            }

            val radioJsonArray = JSONObject(jsonString).getJSONArray("data")

            val filteredRadio = mutableListOf<Radio>()

            for (i in 0 until radioJsonArray.length()) {
                val radioJson = radioJsonArray.getJSONObject(i)
                val title = radioJson.getString("title")
                val cover = radioJson.getString("picture_medium")




                val tracklist = radioJson.getString("tracklist")
                filteredRadio.add(Radio(title, cover, tracklist, false))
            }

            return filteredRadio

        } finally {
            connection.disconnect()
        }
    }
}
