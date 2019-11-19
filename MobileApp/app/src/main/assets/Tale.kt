package com.sogeti.inno.cherry.activities

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Tale(taleJson: JSONObject) : Serializable {


    private lateinit var name : String
    lateinit var content: String


    init {
        try {
            name = taleJson.getString(TALE_NAME)
            content = taleJson.getString(TALE_CONTENT)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TALE_NAME = "name"
        private const val TALE_CONTENT = "content"

    }

}