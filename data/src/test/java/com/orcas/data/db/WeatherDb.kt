package com.orcas.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.orcas.data.locale.AppDataBase
import com.orcas.data.locale.WeatherDao
import com.orcas.data.model.WeatherDataDB
import com.orcas.data.model.weather.Main
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherList
import com.orcas.data.model.weather.Wind
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDb {  @get:Rule
var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: WeatherDao
    private lateinit var database: AppDataBase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).build()
        dao = database.getWeatherDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveWeatherListTest() = runBlocking{
        val list = listOf(WeatherList("title1"), WeatherList(("title2")))
        val main = Main(1.2,5,1,122.1,222.6,100.0)
        val wind=Wind(4.6)
        val weatherData = WeatherData(main,list, wind)

        val weatherDb1 = WeatherDataDB(1,"Cairo",weatherData)
        val weatherDb2 = WeatherDataDB(2,"Alex",weatherData)
        val weatherDb3 = WeatherDataDB(3,"Manspura",weatherData)


        dao.insertData(weatherDb1)
        dao.insertData(weatherDb2)
        dao.insertData(weatherDb3)

        val cairoData = dao.getData(1)
        Truth.assertThat(cairoData).isEqualTo(weatherDb1)
    }

}
