package com.example.ddobagi3.network

import com.example.ddobagi3.model.JusoResponse
import com.example.ddobagi3.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface GetDataAPI {

    @GET("address.json")
    fun getXYByJuso(
        @Header("Authorization") KakaoAK: String = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7",
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("query") query: String
    ):Call<JusoResponse>


    @GET("weather")
    fun getWeather(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("appid") appid : String
    ):Call<WeatherResponse>
}