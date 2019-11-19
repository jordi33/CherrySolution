package com.sogeti.inno.cherry.activities



import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class Activity(activityJson: JSONObject) : Serializable {


    private lateinit var name : String
    private lateinit var description: String


    init {
        try {
            name = activityJson.getString(ACTIVITY_NAME)
            description = activityJson.getString(ACTIVITY_DESCRIPTION)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ACTIVITY_NAME = "name"
        private const val ACTIVITY_DESCRIPTION = "description"

    }

}