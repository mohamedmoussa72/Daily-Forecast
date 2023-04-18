package com.orcas.data.repo.weather.remoteDataSource

import com.orcas.data.model.weather.City
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherDataResponse
import com.orcas.data.network.ApiResponse

interface WeatherRemoteDataSource {
    suspend fun getWeatherDataResponse(lat : Double, lon : Double): ApiResponse<WeatherDataResponse<WeatherData,City>>

}