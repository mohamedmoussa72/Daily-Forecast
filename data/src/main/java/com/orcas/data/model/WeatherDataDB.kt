package com.orcas.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.utile.ConstantData
import java.io.Serializable

@Entity(tableName = ConstantData.WEATHER_TABLE)
data class WeatherDataDB (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int=0,
    @SerializedName("citName") var citName: String ="",
    @SerializedName("weatherData") var weatherData: WeatherData


): Serializable
//{
//    @SerializedName("degree") var degree: String="",
//    @SerializedName("degree_status") var degreeStatus: String="",
//    @SerializedName("degree_min") var degreeMin: String="",
//    @SerializedName("degree_max") var degreeMax: String="",
//    @SerializedName("wind_speed") var windSpeed: String=""
//}