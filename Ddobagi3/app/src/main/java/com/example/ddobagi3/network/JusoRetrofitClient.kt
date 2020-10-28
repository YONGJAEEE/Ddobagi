package com.example.ddobagi3.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JusoRetrofitClient {
    private var instance: Retrofit? = null

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/v2/local/search/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val GetData : GetDataAPI = retrofit.create(GetDataAPI::class.java)

    companion object {
        val instance = JusoRetrofitClient()
    }
}