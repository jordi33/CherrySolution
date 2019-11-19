package com.sogeti.inno.cherry.activities.network

import com.sogeti.inno.cherry.activities.models.Child
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = ""   // Insert URL API

interface ChildApi {

    @GET("")  // insert  API/value
    fun getChild() : Call<List<Child>>



    companion object {
        operator fun invoke() : ChildApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChildApi::class.java)
        }
    }


}

