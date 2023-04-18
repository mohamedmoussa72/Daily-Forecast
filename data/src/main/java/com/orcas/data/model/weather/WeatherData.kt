package com.orcas.data.model.weather


import com.google.gson.annotations.SerializedName


data class WeatherData(

    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weatherList: List<WeatherList>,
    @SerializedName("wind")
    val wind: Wind
)