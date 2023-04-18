package com.orcas.data.repo.weather.remoteDataSource

import androidx.annotation.WorkerThread
import com.orcas.data.model.weather.City
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.network.api.WeatherApiService
import com.orcas.data.model.weather.WeatherDataResponse
import com.orcas.data.network.*
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor( private val weatherApiService: WeatherApiService):
    WeatherRemoteDataSource {


    @WorkerThread
    override suspend fun getWeatherDataResponse( lat: Double, lon: Double): ApiResponse<WeatherDataResponse<WeatherData,City>> {
        return weatherApiService.getWeatherResponse(lat, lon)
    }

}