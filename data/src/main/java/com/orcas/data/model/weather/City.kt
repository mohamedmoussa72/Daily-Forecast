package com.orcas.data.model.weather

import com.google.gson.annotations.SerializedName

data class City (
    @SerializedName("name")
    val name: String=""
)