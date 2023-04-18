package com.orcas.data.model.weather

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feels_like: Double = 0.0,
    @SerializedName("humidity")
    val humidity: Int = 0,
    @SerializedName("pressure")
    val pressure: Int = 0,
    @SerializedName("temp")
    val temp: Double = 0.0,
    @SerializedName("temp_max")
    val temp_max: Double = 0.0,
    @SerializedName("temp_min")
    val temp_min: Double = 0.0
)