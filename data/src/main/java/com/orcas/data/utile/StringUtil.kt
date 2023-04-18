package com.orcas.data.utile

import android.app.Application
import com.orcas.data.R

class StringUtil(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
    fun noCitiesStoredAtDataBase() = appContext.getString(R.string.message_no_city_at_database)
}