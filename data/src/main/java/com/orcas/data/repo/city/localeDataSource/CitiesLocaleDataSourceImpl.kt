package com.orcas.data.repo.city.localeDataSource

import com.orcas.data.locale.CityDao
import com.orcas.data.model.city.City
import com.orcas.data.utile.DataResult
import com.orcas.data.utile.StringUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CitiesLocaleDataSourceImpl @Inject constructor(
    private val cityDao: CityDao
) : CitiesLocaleDataSource {
    override suspend fun saveCityToDB(cityList: List<City>)  {
        cityDao.insert(cityList)
    }

    override suspend fun getSavedCitiesData(): Flow<List<City>> {
       return cityDao.getAllData()
    }
}