package com.orcas.dailyforecast.di.weather

import com.orcas.data.locale.WeatherDao
import com.orcas.data.repo.weather.localeDataSource.WeatherLocaleDataSource
import com.orcas.data.repo.weather.localeDataSource.WeatherLocaleDataSourceImpl
import com.orcas.data.utile.StringUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WeatherLocaleDataModule {
    @Singleton
    @Provides
    fun provideWeatherLocalDataSource( weatherDao: WeatherDao): WeatherLocaleDataSource {
        return WeatherLocaleDataSourceImpl(weatherDao)
    }

}