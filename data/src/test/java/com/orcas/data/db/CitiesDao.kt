package com.orcas.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.orcas.data.locale.AppDataBase
import com.orcas.data.locale.CityDao
import com.orcas.data.model.city.City
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CitiesDao {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: CityDao
    private lateinit var database: AppDataBase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).build()
        dao = database.getCityDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveCitiesTest() = runBlocking{
        val citiesList = listOf(
            City(1,"Cairo",31.565656,33.31467),
            City(2,"Alex",29.565656,31.31467),
            City(3,"Mansoura",25.565656,27.31467)
        )
        dao.insert(citiesList)

        val allCities = dao.getAllData()
        Truth.assertThat(allCities).isEqualTo(citiesList)
    }

}
