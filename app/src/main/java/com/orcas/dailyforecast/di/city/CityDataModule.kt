package com.orcas.dailyforecast.di.city

import com.orcas.data.repo.city.CityRepository
import com.orcas.data.repo.city.CityRepositoryImpl
import com.orcas.data.repo.city.localeDataSource.CitiesLocaleDataSource
import com.orcas.data.repo.city.remoteDataSource.CitiesRemoteDataSource
import com.orcas.data.utile.CoroutineAppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CityDataModule {

    @Provides
    fun provideCityRepository(
        citiesRemoteDataSource: CitiesRemoteDataSource,
        citiesLocaleDataSource: CitiesLocaleDataSource,
        coroutineAppExecutors: CoroutineAppExecutors
    ): CityRepository {
        return CityRepositoryImpl(citiesRemoteDataSource,citiesLocaleDataSource,coroutineAppExecutors)
    }

}