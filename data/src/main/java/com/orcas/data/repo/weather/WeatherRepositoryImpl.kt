package com.orcas.data.repo.weather

import android.content.Context
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.network.*
import com.orcas.data.repo.weather.localeDataSource.WeatherLocaleDataSource
import com.orcas.data.repo.weather.remoteDataSource.WeatherRemoteDataSource
import com.orcas.data.utile.CoroutineAppExecutors
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(

    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocaleDataSource: WeatherLocaleDataSource,
    private val coroutineAppExecutors: CoroutineAppExecutors
) : WeatherRepository {
    override suspend fun getWeatherData(context: Context,cityId:Int, lat: Double, lon: Double): Flow<DataResult<WeatherDataDB>> = networkBoundResource(
       context,
        coroutineAppExecutors = coroutineAppExecutors,
        saveCallResult = { response ->
            WeatherDataDB(cityId,response.city.name,response.list[0]).let {
                weatherLocaleDataSource.saveWeatherToDB(weatherData = it)
            }
        },
        loadFromDb = { weatherLocaleDataSource.getSavedWeatherData(cityId.toInt()) },
        fetch = { weatherRemoteDataSource.getWeatherDataResponse(lat,lon) }
    )

}