package com.orcas.dailyforecast.di.weather

import com.orcas.data.network.api.WeatherApiService
import com.orcas.data.repo.weather.remoteDataSource.WeatherRemoteDataSource
import com.orcas.data.repo.weather.remoteDataSource.WeatherRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object WeatherRemoteDataModule {

    @Provides
    fun provideWeatherRemoteDataSource(
        weatherApiService: WeatherApiService
    ): WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(weatherApiService)
    }

}
