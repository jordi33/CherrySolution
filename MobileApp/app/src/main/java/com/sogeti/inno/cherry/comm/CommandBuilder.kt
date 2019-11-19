package com.sogeti.inno.cherry.comm

import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject

object CommandBuilder{

    fun toJson(c:Command): String? {
        return Gson().toJson(c)
    }

    fun build(text: String): Command? {
        return when {
            text.contains("ConnectedPoppies") -> Gson().fromJson(text, RetrieveConnectedPoppiesResponse::class.java)
            text.contains("PoppyId") -> Gson().fromJson(text, ConnectToPoppyResponse::class.java)
            text.contains("Disconnected") -> Gson().fromJson(text, ConnectionToPoppyLost::class.java)
            text.contains("Jokes") -> Gson().fromJson(text, JokesResponse::class.java)
            text.contains("CalculatorItems") -> Gson().fromJson(text, CalculatorItemsResponse::class.java)
            text.contains("Choregraphies") -> Gson().fromJson(text, GetChoregraphiesResponse::class.java)
            text.contains("Tales") -> Gson().fromJson(text, GetTalesResponse::class.java)
            else -> TestCommand()
        }
    }
}