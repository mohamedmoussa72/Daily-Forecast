package com.orcas.data.api

import com.orcas.data.network.api.CitiesApiService
import kotlinx.coroutines.runBlocking
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.orcas.data.model.city.CitiesResponse
import com.orcas.data.model.city.City
import com.orcas.data.network.ApiResponseCallAdapterFactory
import com.orcas.data.network.ApiSuccessResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class CitiesAPIServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: CitiesApiService

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
            .create(CitiesApiService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()

        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `Load Cities OK`() = runBlocking(Dispatchers.Main) {
        enqueueResponse("citiesresponse.json")

        val data = service.getCities()

        val request = mockWebServer.takeRequest()

        val result = (data as ApiSuccessResponse<*>).body
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/cities.json"))

        val city: CitiesResponse<City> = result as CitiesResponse<City>
        MatcherAssert.assertThat(city.cities?.get(0)?.id.toString(), CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(city.cities?.get(0)?.cityNameEn.toString(), CoreMatchers.`is`("Cairo"))
    }


    private fun enqueueResponse(fileName: String) {

        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockWebServer.enqueue(mockResponse)
    }

}