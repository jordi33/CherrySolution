package com.sogeti.inno.cherry.activities


import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Movement(movementJson: JSONObject) : Serializable {


    private lateinit var name : String
    private lateinit var directory: String
    private lateinit var image: String


    init {
        try {
            name = movementJson.getString(MOVEMENT_NAME)
            directory = movementJson.getString(MOVEMENT_DIRECTORY)
            image = movementJson.getString(MOVEMENT_IMAGE)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val MOVEMENT_NAME = "name"
        private const val MOVEMENT_DIRECTORY = "directory"
        private const val MOVEMENT_IMAGE = "image"

    }

}