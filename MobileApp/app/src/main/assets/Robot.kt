package com.sogeti.inno.cherry.activities

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Robot(robotJson: JSONObject) : Serializable {


    private lateinit var wifiName : String
    var version: Double = 0.0


    init {
        try {
            wifiName = robotJson.getString(ROBOT_WIFINAME)
            version = robotJson.getDouble(ROBOT_VERSION)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ROBOT_WIFINAME = "wifiName"
        private const val ROBOT_VERSION = "version"

    }

}