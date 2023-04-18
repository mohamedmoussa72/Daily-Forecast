package com.orcas.data.network.api


import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.city.City
import com.orcas.data.network.ApiResponse
import retrofit2.http.GET

interface CitiesApiService {
    @GET("cities.json")
    suspend fun getCities(): ApiResponse<CitiesResponse<City>>
}