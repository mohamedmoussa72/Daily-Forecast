package com.orcas.data.repo.weather

import android.content.Context
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherDataResponse
import kotlinx.coroutines.flow.Flow
import com.orcas.data.utile.*

interface WeatherRepository {
    suspend fun getWeatherData(context: Context,cityId:Int,lat: Double, lon: Double):Flow<DataResult<WeatherDataDB>>

}