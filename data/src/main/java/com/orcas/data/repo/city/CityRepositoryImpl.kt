package com.orcas.data.repo.city

import android.content.Context
import android.util.Log
import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.city.City
import com.orcas.data.network.ApiResponse
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
            Log.e("TagCityRe",response.cities.toString()+" bbb")
            response.cities?.let { citiesLocaleDataSource.saveCityToDB(cityList = it) }
        },
        loadFromDb = {
            Log.e("TagCityRe","load")
            citiesLocaleDataSource.getSavedCitiesData() },
        fetch = {
            Log.e("TagCityRe","fetch")

            citiesRemoteDataSource.getCities()
        }
    )

}