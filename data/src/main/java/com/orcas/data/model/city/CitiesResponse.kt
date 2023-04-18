package com.orcas.data.model.city

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CitiesResponse<T>(
    @SerializedName("cities")
    val cities: List<T>?
): Serializable