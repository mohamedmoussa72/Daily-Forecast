package com.orcas.data.network.api


import com.orcas.data.BuildConfig
import com.orcas.data.model.weather.City
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherDataResponse
import com.orcas.data.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeatherResponse(
        @Query("lat")
        lat:Double,
        @Query("lon")
        lon:Double,
        @Query("appid")
        apiKey:String = BuildConfig.API_KEY
    ): ApiResponse<WeatherDataResponse<WeatherData,City>>
}