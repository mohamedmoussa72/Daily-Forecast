package com.orcas.data.utile

import com.orcas.data.network.ApiResponse
import okhttp3.ResponseBody
import org.mockito.Mockito
import retrofit2.Response

object ApiUtil {

    fun <T : Any> successCall(data: T) = ApiResponse.create(Response.success(data))

    fun <T : Any> errorCall() = ApiResponse.create<T>(Response.error(500, Mockito.mock(ResponseBody::class.java)))
}
