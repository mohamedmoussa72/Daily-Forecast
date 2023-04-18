package com.orcas.dailyforecast.di.db

import android.app.Application
import androidx.room.Room
import com.orcas.data.locale.WeatherDao
import com.orcas.data.locale.AppDataBase
import com.orcas.data.locale.CityDao
import com.orcas.data.utile.ConstantData.WEATHER_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideWeatherDatabase(app: Application): AppDataBase {
        return Room.databaseBuilder(app, AppDataBase::class.java, WEATHER_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: AppDataBase): WeatherDao {
        return weatherDataBase.getWeatherDao()
    }
    @Singleton
    @Provides
    fun provideCityDao(weatherDataBase: AppDataBase): CityDao {
        return weatherDataBase.getCityDao()
    }
}