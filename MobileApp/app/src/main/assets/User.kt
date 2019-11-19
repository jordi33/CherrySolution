package com.sogeti.inno.cherry.activities

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class User(userJson: JSONObject) : Serializable {


    private lateinit var login: String
    lateinit var password: String


    init {
        try {
            login = userJson.getString(USER_LOGIN)
            password = userJson.getString(USER_PASSWORD)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val USER_LOGIN = "name"
        private const val USER_PASSWORD = "password"

    }

}