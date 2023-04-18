package com.orcas.data.network

import android.content.Context
import androidx.annotation.MainThread
import com.orcas.data.R
import com.orcas.data.utile.CoroutineAppExecutors
import com.orcas.data.utile.DataResult

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Read data from database and API WS
 */
fun <ResultType, RequestType> networkBoundResource(
    context: Context,
    coroutineAppExecutors: CoroutineAppExecutors,
    saveCallResult: suspend (RequestType) -> Unit,
    shouldFetch: () -> Boolean = { true },
    loadFromDb: suspend () -> Flow<ResultType>,
    fetch: suspend () -> ApiResponse<RequestType>,
    processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType) = { it.body },
    onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)? = null
): Flow<DataResult<ResultType>> {
    return NetworkBoundResource(
        context,
        coroutineAppExecutors = coroutineAppExecutors,
        saveCallResult = saveCallResult,
        shouldFetch = shouldFetch,
        loadFromDb = loadFromDb,
        fetch = fetch,
        processResponse = processResponse,
        onFetchFailed = onFetchFailed
    ).asFlow()
}

private class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(
    context:Context,
    private val coroutineAppExecutors: CoroutineAppExecutors,
    private val saveCallResult: suspend (RequestType) -> Unit,
    private val shouldFetch: () -> Boolean = { true },
    private val loadFromDb: suspend () -> Flow<ResultType>,
    private val fetch: suspend () -> ApiResponse<RequestType>,
    private val processResponse: (suspend (ApiSuccessResponse<RequestType>) -> RequestType),
    private val onFetchFailed: ((ApiErrorResponse<RequestType>) -> Unit)?
) {

    private val result: Flow<DataResult<ResultType>> = channelFlow {

        if (!shouldFetch()) {
            loadFromDb().map { DataResult.success(it) }.collect {
                send(it)
            }
        } else {
            doFetch(context,this)
        }
    }

    private suspend fun doFetch(context: Context,collector: ProducerScope<DataResult<ResultType>>) =
        withContext(coroutineAppExecutors.diskIODispatcher()) {

            val firstResult = loadFromDb().firstOrNull()
            firstResult.apply {
                collector.send(DataResult.loading(firstResult))
            }

            when (val response = fetchCatching()) {

                is ApiSuccessResponse, is ApiEmptyResponse -> {
                    if (response is ApiSuccessResponse) {
                        if (response.body.toString().split("(")[0] != "ApiErrorResponse") {
                            val processed = processResponse(response)
                            saveCallResult(processed)
                        }else{
                            collector.send(DataResult.noNetwork(firstResult))
                        }

                    }

                    loadFromDb().collect {

                        collector.send(DataResult.success(it))
                        if (it.toString() == "null") {
                            loadFromDb().collect {
                                collector.send(DataResult.error(context.getString(R.string.message_no_network_connected_str), it))
                            }
                        }

                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed?.invoke(response)
                    loadFromDb().collect {
                        collector.send(DataResult.error(response.errorMessage, it))
                    }
                }
            }
        }

    private suspend fun fetchCatching(): ApiResponse<RequestType> {
        return try {
            fetch()
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            ApiResponse.create(ex)
        }
    }

    fun asFlow(): Flow<DataResult<ResultType>> = result
}