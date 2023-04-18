package com.orcas.data.model.weather

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherDataResponse<T,N>(
    @SerializedName("cod")
    val cod: String= "",
    @SerializedName("list")
    val list: List<T>,
    @SerializedName("message")
    val message: Int = 0 ,
    @SerializedName("city")
    val city: N
): Serializable