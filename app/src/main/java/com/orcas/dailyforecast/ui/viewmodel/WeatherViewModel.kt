package com.orcas.dailyforecast.ui.viewmodel

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcas.dailyforecast.utile.wrapEspressoIdlingResource
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.utile.DataResult
import com.orcas.domain.usecase.city.GetCitiesUseCase
import com.orcas.domain.usecase.weather.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel  @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase): ViewModel(){
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val weatherLiveDataPrivate = MutableLiveData<DataResult<WeatherDataDB>>()
     val weatherLiveData: LiveData<DataResult<WeatherDataDB>> get() = weatherLiveDataPrivate

    fun getWeather(context: Context, cityId:Int, lat: Double, lon: Double) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                getWeatherUseCase.execute(context,cityId,lat,lon).collect {
                    weatherLiveDataPrivate.value = it
                }
            }
        }
    }

}