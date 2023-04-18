package com.orcas.data.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherList(
    @SerializedName("main")
    val main: String=""
)