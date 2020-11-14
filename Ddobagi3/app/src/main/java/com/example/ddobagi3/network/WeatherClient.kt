package com.example.ddobagi3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherClient {
    private var instance: Retrofit? = null

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GetData : GetDataAPI = retrofit.create(GetDataAPI::class.java)

    companion object {
        val instance = WeatherClient()
    }
}