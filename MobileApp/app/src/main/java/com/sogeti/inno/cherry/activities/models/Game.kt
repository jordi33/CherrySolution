package com.sogeti.inno.cherry.activities.models

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

data class Game(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val category: String
)