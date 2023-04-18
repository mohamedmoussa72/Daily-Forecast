package com.orcas.dailyforecast.di.weather

import com.orcas.data.repo.weather.WeatherRepository
import com.orcas.data.repo.weather.WeatherRepositoryImpl
import com.orcas.data.repo.weather.localeDataSource.WeatherLocaleDataSource
import com.orcas.data.repo.weather.remoteDataSource.WeatherRemoteDataSource
import com.orcas.data.utile.CoroutineAppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WeatherDataModule {

    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
        weatherLocaleDataSource: WeatherLocaleDataSource,
        coroutineAppExecutors: CoroutineAppExecutors
    ): WeatherRepository {
        return WeatherRepositoryImpl(weatherRemoteDataSource,weatherLocaleDataSource,coroutineAppExecutors)
    }

}