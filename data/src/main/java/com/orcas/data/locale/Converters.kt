package com.orcas.data.locale

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.orcas.data.model.weather.Main
import com.orcas.data.model.weather.WeatherData
import com.orcas.data.model.weather.WeatherList
import com.orcas.data.model.weather.Wind

class Converters {
    @TypeConverter
    fun fromMaintoJSONString(main: Main): String = Gson().toJson(main)
    @TypeConverter
    fun toMainFromJSONString(jsonString: String): Main = Gson().fromJson(jsonString,Main::class.java)
    @TypeConverter
    fun fromWeatherListFromJSONString(weatherList: WeatherList): String = Gson().toJson(weatherList)
    @TypeConverter
    fun toWeatherListFromJSOnString(jsonString: String): WeatherList = Gson().fromJson(jsonString,WeatherList::class.java)
    @TypeConverter
    fun fromWindToJSONString(wind: Wind): String = Gson().toJson(wind)
    @TypeConverter
    fun toWindFromJSONString(jsonString: String): Wind = Gson().fromJson(jsonString,Wind::class.java)

    @TypeConverter
    fun fromWeatherDataToJSONString(weatherData: WeatherData): String = Gson().toJson(weatherData)
    @TypeConverter
    fun toWeatherDataFromJSONString(jsonString: String): WeatherData = Gson().fromJson(jsonString,WeatherData::class.java)
}