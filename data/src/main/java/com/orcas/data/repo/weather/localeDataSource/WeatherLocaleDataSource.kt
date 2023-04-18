package com.orcas.data.repo.weather.localeDataSource

import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow

interface WeatherLocaleDataSource{
    suspend fun saveWeatherToDB(weatherData: WeatherDataDB)
   suspend fun getSavedWeatherData(cityId:Int): Flow<WeatherDataDB>
}