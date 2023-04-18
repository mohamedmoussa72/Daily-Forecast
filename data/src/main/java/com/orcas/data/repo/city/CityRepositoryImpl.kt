package com.orcas.data.repo.city

import android.content.Context
import com.orcas.data.model.city.City
import com.orcas.data.network.networkBoundResource
import com.orcas.data.repo.city.localeDataSource.CitiesLocaleDataSource
import com.orcas.data.repo.city.remoteDataSource.CitiesRemoteDataSource
import com.orcas.data.utile.CoroutineAppExecutors
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val citiesRemoteDataSource: CitiesRemoteDataSource,
    private val citiesLocaleDataSource: CitiesLocaleDataSource,
    private val coroutineAppExecutors: CoroutineAppExecutors
) : CityRepository {
    override suspend fun getCities(context: Context): Flow<DataResult<List<City>>> = networkBoundResource(
        context,
        coroutineAppExecutors = coroutineAppExecutors,
        saveCallResult = { response ->
            response.cities?.let { citiesLocaleDataSource.saveCityToDB(cityList = it) }
        },
        loadFromDb = {
            citiesLocaleDataSource.getSavedCitiesData() },
        fetch = {
            citiesRemoteDataSource.getCities()
        }
    )

}