package com.orcas.data.repo.city.remoteDataSource

import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.city.City
import com.orcas.data.network.ApiResponse


interface CitiesRemoteDataSource {
   suspend fun getCities():  ApiResponse<CitiesResponse<City>>

}