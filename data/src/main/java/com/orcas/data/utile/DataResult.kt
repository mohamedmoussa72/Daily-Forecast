package com.orcas.data.utile


data class DataResult<out T>(val status: Status, val data: T?, val message: String? = null) {

    companion object {

        fun <T> success(data: T?): DataResult<T> {
            return DataResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): DataResult<T> {
            return DataResult(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): DataResult<T> {
            return DataResult(Status.LOADING, data, null)
        }

        fun <T> noNetwork(data: T?): DataResult<T> {
            return DataResult(Status.No_NETWORK, data, null)
        }


    }

    fun isFinalStatus(): Boolean {
        return status == Status.SUCCESS || status == Status.ERROR
    }

   public fun isLoading(): Boolean {
        return status == Status.LOADING
    }


}