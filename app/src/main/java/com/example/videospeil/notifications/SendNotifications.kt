package com.example.videospeil.notifications

import org.json.JSONException
import org.json.JSONObject

class SendNotifications(message: String, heading: String, key: String) {
    init {
        try {
            val obj = JSONObject(
                "{'contents':{'en':'" + message + "'}," +
                        "'include_player_ids':['" + key + "']," +
                        "'headings':{'en': '" + heading + "'}}"
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}