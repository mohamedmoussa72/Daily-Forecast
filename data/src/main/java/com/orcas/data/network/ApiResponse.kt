package com.orcas.data.network

import android.util.Log
import retrofit2.Response
import timber.log.Timber

/**
 * Common class used by API responses.
 * @param <T>
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
//                Log.e("TagDatannn",body.toString()+" bbb"+response.code())

//                if (  response.body().toString().split("(")[0] == "ApiErrorResponse"
//                            || response.body().toString().split("(")[0] == "CitiesResponse"
//                            ||  response.body().toString().split("(")[0] == "WeatherDataResponse"){
//                }

                if (body == null || response.code() == 204) {
                    ApiEmptyResponse("empty")
                } else {
                    if (body is ApiSuccessResponse<*>) {
                        ApiSuccessResponse(body = (body as ApiSuccessResponse<T>).body,"api")
                    } else {
                        ApiSuccessResponse(body = body,"body")
                    }
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                Timber.w("WS ERROR %d Message: %s", response.code(), errorMsg ?: "unknown error")
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> (val message: String) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T , val successMessage:String) : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()