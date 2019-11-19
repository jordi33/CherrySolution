package com.sogeti.inno.cherry.activities.models

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//class Joke(jokeJSON: JSONObject) : Serializable {
//
//    lateinit var jokeName: String
//    lateinit var jokeContent: String
//    lateinit var url: String
//        private set
//
//    init {
//        try {
//            jokeName = jokeJSON.getString(JOKE_NAME)
//            jokeContent = jokeJSON.getString(JOKE_CONTENT)
//            url = jokeJSON.getString(JOKE_URL)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//
//    }
//
//
//    companion object {
//        private val JOKE_NAME = "name"
//        private val JOKE_CONTENT = "content"
//        private val JOKE_URL = "url"
//    }
//}







/**
data class Joke(
    val id: Int,
    val name: String,
    val content: String
)
        **/

/**

class Joke {
    var id: Int? = null
    var name: String? = null
    var content: String? = null

            constructor(id: Int, name: String, content: String) {
                this.id = id
                this.name = name
                this.content = content
            }
}

        **/