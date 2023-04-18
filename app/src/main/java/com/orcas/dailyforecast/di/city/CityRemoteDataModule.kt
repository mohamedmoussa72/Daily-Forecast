package com.orcas.dailyforecast.di.city


import com.orcas.data.network.api.CitiesApiService
import com.orcas.data.repo.city.remoteDataSource.CitiesRemoteDataSource
import com.orcas.data.repo.city.remoteDataSource.CitiesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CityRemoteDataModule {

    @Provides
    fun provideCityRemoteDataSource(
        citiesApiService: CitiesApiService
    ): CitiesRemoteDataSource {
        return CitiesRemoteDataSourceImpl(citiesApiService)
    }

}
