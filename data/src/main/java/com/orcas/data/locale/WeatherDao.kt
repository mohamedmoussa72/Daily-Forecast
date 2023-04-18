package com.orcas.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.utile.ConstantData.WEATHER_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM $WEATHER_TABLE WHERE id =:id" )
     fun getData(id:Int):  WeatherDataDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(weatherDataDB: WeatherDataDB)
}