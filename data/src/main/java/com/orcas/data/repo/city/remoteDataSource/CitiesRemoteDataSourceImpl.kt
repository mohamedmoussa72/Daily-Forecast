package com.orcas.data.repo.city.remoteDataSource

import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.city.City
import com.orcas.data.network.*
import com.orcas.data.network.api.CitiesApiService
import timber.log.Timber
import javax.inject.Inject

class CitiesRemoteDataSourceImpl @Inject constructor(
     private val citiesApiService: CitiesApiService
) : CitiesRemoteDataSource {


    override suspend fun getCities():  ApiResponse<CitiesResponse<City>>{
         try {
            citiesApiService.getCities()
        }catch (e:Exception) {
            Timber.e(e.message.toString())
        }
        return citiesApiService.getCities()

    }
}