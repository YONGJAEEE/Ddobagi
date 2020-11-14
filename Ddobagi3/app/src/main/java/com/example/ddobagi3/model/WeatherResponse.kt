package com.example.ddobagi3.model

data class WeatherResponse(
    val weather: List<Weather>?,
    val name: String?,
    val cod: Int?
)

data class Weather(
    val id: Int?,
    val main: String?,
    val description: String?,
    val icon: String?
)

