package com.orcas.data.repo.city.localeDataSource

import com.orcas.data.model.city.City
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow

interface CitiesLocaleDataSource {
    suspend fun saveCityToDB(cityList: List<City>)
    suspend fun getSavedCitiesData(): Flow<List<City>>
}