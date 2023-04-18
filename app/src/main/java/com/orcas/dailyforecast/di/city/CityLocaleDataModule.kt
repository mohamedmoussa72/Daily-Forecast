package com.orcas.dailyforecast.di.city


import com.orcas.data.locale.CityDao
import com.orcas.data.repo.city.localeDataSource.CitiesLocaleDataSource
import com.orcas.data.repo.city.localeDataSource.CitiesLocaleDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CityLocaleDataModule {
    @Singleton
    @Provides
    fun provideCityLocalDataSource(cityDao: CityDao): CitiesLocaleDataSource {
        return CitiesLocaleDataSourceImpl(cityDao)
    }

}