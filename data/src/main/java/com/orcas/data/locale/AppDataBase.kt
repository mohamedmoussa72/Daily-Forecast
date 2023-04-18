package com.orcas.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.city.City


@TypeConverters( value = [Converters::class])
@Database(entities = [WeatherDataDB::class,City::class],
    version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getWeatherDao(): WeatherDao
    abstract fun getCityDao(): CityDao

}