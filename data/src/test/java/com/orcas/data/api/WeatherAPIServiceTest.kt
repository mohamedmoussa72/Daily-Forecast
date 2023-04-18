package com.orcas.data.api

import kotlinx.coroutines.runBlocking
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.common.truth.Truth.assertThat
import com.orcas.data.BuildConfig
import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.weather.City
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherDataResponse
import com.orcas.data.network.ApiResponseCallAdapterFactory
import com.orcas.data.network.ApiSuccessResponse
import com.orcas.data.network.api.CitiesApiService
import com.orcas.data.network.api.WeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

class WeatherAPIServiceTest {
    private lateinit var service: WeatherApiService
    private lateinit var mockWebServer: MockWebServer


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun createService() {
        Dispatchers.setMain(mainThreadSurrogate)

        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .build()
            .create(WeatherApiService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }


    @Test
    fun `Load WeatherData OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("weatherresponse.json")

        val data = service.getWeatherResponse(30.0444,31.2357,BuildConfig.API_KEY)

        val request = mockWebServer.takeRequest()

        val result = (data as ApiSuccessResponse<*>).body
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/forecast?lat=30.0444&lon=31.2357&appid=e7e21f49f0d7a9b62fa6bb76da2b482c"))

        val weatherData: WeatherDataResponse<WeatherData, City> = result as WeatherDataResponse<WeatherData, City>
        MatcherAssert.assertThat(weatherData.city.name, CoreMatchers.`is`("Al ‘Atabah"))
    }


    private fun enqueueResponse(fileName: String) {

        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }


}