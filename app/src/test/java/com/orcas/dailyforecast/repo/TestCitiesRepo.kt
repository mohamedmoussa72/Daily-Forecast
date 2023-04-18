package com.orcas.dailyforecast.repo

import android.content.Context
import com.orcas.data.model.city.City
import com.orcas.data.repo.city.CityRepository
import com.orcas.data.utile.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestCitiesRepo :CityRepository{
    private val citiesList = mutableListOf<City>()

    init {
        citiesList.add(City(1,"Cairo",31.565656,33.31467))
        citiesList.add(City(2,"Alex",29.565656,31.31467))
        citiesList.add(City(3,"Mansoura",27.565656,29.31467))

    }

    override suspend fun getCities(context: Context): Flow<DataResult<List<City>>>? {
        return flow {
            emit(DataResult.success(citiesList)) }
    }


}