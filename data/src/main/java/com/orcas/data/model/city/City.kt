package com.orcas.data.model.city

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.orcas.data.utile.ConstantData
import java.io.Serializable

@Entity(tableName = ConstantData.CITY_TABLE)

data class City(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int=0,
    @SerializedName("cityNameEn")
    val cityNameEn: String="",
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lon")
    val lon: Double = 0.0
): Serializable