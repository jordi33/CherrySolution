package com.sogeti.inno.cherry.activities.network

//import com.sogeti.inno.cherry.activities.models.Joke
import com.sogeti.inno.cherry.comm.Joke
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val JOKE_API_URL = ""   // Insert URL API

interface JokeApi {

    @GET("")  // insert  API/value
    fun getJoke() : Call<List<Joke>>

    companion object {
        operator fun invoke() : JokeApi {
            return Retrofit.Builder()
                .baseUrl(JOKE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokeApi::class.java)
        }
    }
}