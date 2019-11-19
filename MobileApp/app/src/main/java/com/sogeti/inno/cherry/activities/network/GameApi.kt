package com.sogeti.inno.cherry.activities.network

import com.sogeti.inno.cherry.activities.models.Game
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val GAME_API_URL = ""   // Insert URL API

interface GameApi {

    @GET("")  // insert  API/value
    fun getGame() : Call<List<Game>>

    companion object {
        operator fun invoke() : GameApi {
            return Retrofit.Builder()
                .baseUrl(GAME_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GameApi::class.java)
        }
    }
}