package com.orcas.domain.usecase.city

import android.content.Context
import com.orcas.data.model.city.City
import com.orcas.data.repo.city.CityRepository
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase  @Inject constructor(private val citRepository: CityRepository){
    suspend fun execute(context: Context): Flow<DataResult<List<City>>>?{
        return citRepository.getCities(context)
    }

}