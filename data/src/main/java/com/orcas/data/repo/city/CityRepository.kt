package com.orcas.data.repo.city

import android.content.Context
import com.orcas.data.model.city.City
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun getCities(context: Context): Flow<DataResult<List<City>>>?

}