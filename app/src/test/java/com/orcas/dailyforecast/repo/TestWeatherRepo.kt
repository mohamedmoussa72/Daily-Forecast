package com.orcas.dailyforecast.repo

import android.content.Context
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.weather.Main
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherList
import com.orcas.data.model.weather.Wind
import com.orcas.data.repo.weather.WeatherRepository
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestWeatherRepo :WeatherRepository{
    private lateinit var weatherDataDB :WeatherDataDB

    init {
        val list = listOf(WeatherList("title1"), WeatherList(("title2")))
        val main = Main(1.2,5,1,122.1,222.6,100.0)
        val wind= Wind(4.6)
        val weatherData = WeatherData(main,list, wind)

        weatherDataDB = WeatherDataDB(1,"Cairo",weatherData)
    }

    override suspend fun getWeatherData(
        context: Context,
        cityId: Int,
        lat: Double,
        lon: Double
    ): Flow<DataResult<WeatherDataDB>> {
        return flow {
            emit(DataResult.success(weatherDataDB)) }

    }

}