package com.orcas.data.network.api.interceptors

import android.content.res.Configuration
import com.orcas.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val configuration: Configuration): Interceptor {

    private val APIKEY_NAME = "api_key"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val originalHttpUrl = request.url

        val apiKey: String = BuildConfig.API_KEY

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(APIKEY_NAME, apiKey)
            .build()



        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }

}