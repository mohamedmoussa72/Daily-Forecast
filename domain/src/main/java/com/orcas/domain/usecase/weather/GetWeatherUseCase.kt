package com.orcas.domain.usecase.weather

import android.content.Context
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.repo.weather.WeatherRepository
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository){
    suspend fun execute(context: Context, cityId:Int, lat: Double, lon: Double): Flow<DataResult<WeatherDataDB>> {
        return weatherRepository.getWeatherData(context,cityId,lat,lon)
    }
}