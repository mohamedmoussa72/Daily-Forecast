package com.orcas.dailyforecast.di.api


import com.orcas.dailyforecast.BuildConfig
import com.orcas.dailyforecast.utile.City
import com.orcas.dailyforecast.utile.Weather
import com.orcas.data.network.ApiResponseCallAdapterFactory
import com.orcas.data.network.api.CitiesApiService
import com.orcas.data.network.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    }
    @Provides
    @Singleton
    @Weather
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    @City
    fun providesCitiesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.CITIES_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun providesWeatherApiService(@Weather retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesCitiesApiService(@City retrofit: Retrofit): CitiesApiService {
        return retrofit.create(CitiesApiService::class.java)

    }
}