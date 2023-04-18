package com.orcas.data.utile

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `Flow<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    No_NETWORK,
    SUCCESS,
    ERROR,
    LOADING
}