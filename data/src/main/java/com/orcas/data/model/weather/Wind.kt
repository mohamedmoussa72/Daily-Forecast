package com.orcas.data.model.weather

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Double = 0.0
)