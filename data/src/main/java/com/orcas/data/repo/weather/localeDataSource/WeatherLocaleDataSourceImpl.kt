package com.orcas.data.repo.weather.localeDataSource

import com.orcas.data.locale.WeatherDao
import com.orcas.data.model.WeatherDataDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherLocaleDataSourceImpl @Inject constructor(private val weatherDao: WeatherDao) :
    WeatherLocaleDataSource {


    override suspend fun saveWeatherToDB(weatherData: WeatherDataDB) {
        weatherDao.insertData(weatherData)
    }

    override suspend fun getSavedWeatherData(cityId: Int):
            Flow<WeatherDataDB> {
        return flow {

            emit(weatherDao.getData(cityId)) }
            }


}