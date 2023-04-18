package com.orcas.dailyforecast.ui.viewmodel

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.orcas.dailyforecast.repo.TestCitiesRepo
import com.orcas.dailyforecast.repo.TestWeatherRepo
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.city.City
import com.orcas.data.model.weather.Main
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherList
import com.orcas.data.model.weather.Wind
import com.orcas.domain.usecase.city.GetCitiesUseCase
import com.orcas.domain.usecase.weather.GetWeatherUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestWeatherViewModel {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WeatherViewModel
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var testWeatherRepo: TestWeatherRepo
    private lateinit var weatherDataDB :WeatherDataDB

    @Before
    fun setUp() {

        testWeatherRepo = TestWeatherRepo()
        getWeatherUseCase = GetWeatherUseCase(testWeatherRepo)
        viewModel = WeatherViewModel(getWeatherUseCase)
    }

    @Test
    fun getWeather_returnsCurrentData() {
        val list = listOf(WeatherList("title1"), WeatherList(("title2")))
        val main = Main(1.2,5,1,122.1,222.6,100.0)
        val wind= Wind(4.6)
        val weatherData = WeatherData(main,list, wind)

        weatherDataDB = WeatherDataDB(1,"Cairo",weatherData)

        CoroutineScope(Dispatchers.IO).launch {
           testWeatherRepo.getWeatherData(Application(),1,30.0444,31.2357).collectLatest {
                Truth.assertThat(it.data).isEqualTo(weatherDataDB)

            }
        }
    }
}