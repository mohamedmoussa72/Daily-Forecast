package com.orcas.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.orcas.data.model.city.City
import com.orcas.data.utile.ConstantData
import kotlinx.coroutines.flow.Flow
@Dao

interface CityDao {
    @Query("SELECT * FROM ${ConstantData.CITY_TABLE}")
    fun getAllData(): Flow<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<City>)
}